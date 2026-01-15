package com.docpet.animalhospital.service;

import com.docpet.animalhospital.service.dto.ChatRequestDTO;
import com.docpet.animalhospital.service.dto.ChatResponseDTO;
import com.docpet.animalhospital.service.dto.DiseaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChatService {

    private static final Logger LOG = LoggerFactory.getLogger(ChatService.class);

    private final DiseaseSearchService diseaseSearchService;
    private final AIService aiService;

    public ChatService(
        DiseaseSearchService diseaseSearchService,
        AIService aiService
    ) {
        this.diseaseSearchService = diseaseSearchService;
        this.aiService = aiService;
    }

    /**
     * Xử lý chat message từ anonymous user
     * @param request Chat request
     * @return Chat response
     */
    public ChatResponseDTO processMessage(ChatRequestDTO request) {
        LOG.info("Processing chat message: {}", request.getMessage());

        // Generate hoặc reuse sessionId
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        try {
            // 1. Detect species từ message
            String species = detectSpecies(request.getMessage());
            LOG.info("Detected species: {}", species);

            if (species == null || species.isEmpty()) {
                LOG.warn("Cannot detect species from message: {}", request.getMessage());
                ChatResponseDTO errorResponse = new ChatResponseDTO();
                errorResponse.setSessionId(sessionId);
                errorResponse.setResponse(
                    "Xin chào! Tôi là bác sĩ thú y AI. " +
                    "Để tôi có thể tư vấn chính xác, vui lòng cho biết thú cưng của bạn là chó hay mèo. " +
                    "Ví dụ: 'chó của tôi bị nôn' hoặc 'mèo bị tiêu chảy'."
                );
                return errorResponse;
            }

            // 2. Map species → table và search disease
            LOG.info("Searching disease for message: '{}', species: {}", request.getMessage(), species);
            List<DiseaseDTO> diseaseList = diseaseSearchService.searchDisease(request.getMessage(), species);
            
            LOG.info("Found {} disease results from database", diseaseList.size());
            if (!diseaseList.isEmpty()) {
                LOG.info("Disease titles: {}", 
                    diseaseList.stream()
                        .map(DiseaseDTO::getTitle)
                        .collect(Collectors.toList()));
            }

            // 3. Build prompt với context
            String prompt = buildPrompt(request.getMessage(), diseaseList, species);

            // 4. Generate response từ AI
            String aiResponse;
            if (aiService.isAvailable()) {
                try {
                    aiResponse = aiService.generateResponse(prompt, null);
                    LOG.debug("AI response generated successfully");
                } catch (Exception e) {
                    LOG.error("Error generating AI response, using fallback", e);
                    aiResponse = buildFallbackResponse(diseaseList, request.getMessage());
                }
            } else {
                LOG.warn("AI service not available, using fallback");
                aiResponse = buildFallbackResponse(diseaseList, request.getMessage());
            }

            // 5. Create response
            ChatResponseDTO response = new ChatResponseDTO(aiResponse, sessionId);
            return response;

        } catch (Exception e) {
            LOG.error("Error processing chat message", e);
            // Return error message
            ChatResponseDTO errorResponse = new ChatResponseDTO();
            errorResponse.setSessionId(sessionId);
            errorResponse.setResponse(
                "Xin lỗi, đã có lỗi xảy ra khi xử lý câu hỏi của bạn. " +
                "Vui lòng thử lại sau hoặc liên hệ trực tiếp với bác sĩ thú y."
            );
            return errorResponse;
        }
    }

    /**
     * Detect species (chó/mèo) từ message
     */
    private String detectSpecies(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        String lowerMessage = message.toLowerCase();
        
        // Check for "chó" or "dog"
        if (lowerMessage.contains("chó") || lowerMessage.contains("dog") || 
            lowerMessage.contains("cún") || lowerMessage.contains("cẩu")) {
            return "Chó";
        }
        
        // Check for "mèo" or "cat"
        if (lowerMessage.contains("mèo") || lowerMessage.contains("cat") || 
            lowerMessage.contains("mèo con") || lowerMessage.contains("kitten")) {
            return "Mèo";
        }

        return null; // Không detect được
    }

    /**
     * Build prompt cho AI với context từ disease database
     */
    private String buildPrompt(String userMessage, List<DiseaseDTO> diseaseList, String species) {
        StringBuilder prompt = new StringBuilder();

        // System prompt
        prompt.append("Bạn là một bác sĩ thú y AI chuyên nghiệp và thân thiện. ");
        prompt.append("Nhiệm vụ của bạn là tư vấn về sức khỏe thú cưng dựa trên kiến thức thú y chính xác.\n\n");
        
        prompt.append("QUAN TRỌNG:\n");
        prompt.append("- Luôn nhấn mạnh rằng đây chỉ là tư vấn sơ bộ, không thay thế khám bác sĩ thú y\n");
        prompt.append("- Nếu có triệu chứng nghiêm trọng (ngộ độc, chảy máu, khó thở, co giật), khuyên đến bác sĩ NGAY LẬP TỨC\n");
        prompt.append("- Trả lời bằng tiếng Việt, dễ hiểu, thân thiện\n");
        prompt.append("- Sử dụng thông tin từ database bệnh được cung cấp bên dưới\n\n");

        // Context từ disease database
        if (!diseaseList.isEmpty()) {
            prompt.append("THÔNG TIN BỆNH LIÊN QUAN (").append(species).append("):\n");
            prompt.append("---\n");
            
            for (int i = 0; i < diseaseList.size(); i++) {
                DiseaseDTO disease = diseaseList.get(i);
                prompt.append(String.format("\n[Bệnh %d]\n", i + 1));
                prompt.append("Tiêu đề: ").append(disease.getTitle()).append("\n");
                
                if (disease.getSeverityLevel() != null) {
                    prompt.append("Mức độ: ").append(disease.getSeverityLevel()).append("\n");
                }
                
                prompt.append("Nội dung: ").append(disease.getContent()).append("\n");
                prompt.append("---\n");
            }
            
            prompt.append("\nHãy sử dụng thông tin trên để trả lời câu hỏi của người dùng.\n\n");
        } else {
            prompt.append("Lưu ý: Không tìm thấy thông tin bệnh cụ thể trong database cho ").append(species).append(". ");
            prompt.append("Hãy trả lời dựa trên kiến thức thú y chung của bạn, ");
            prompt.append("nhưng luôn nhấn mạnh cần tham khảo ý kiến bác sĩ thú y.\n\n");
        }

        // User message
        prompt.append("CÂU HỎI CỦA NGƯỜI DÙNG:\n");
        prompt.append("(Loài thú cưng: ").append(species).append(")\n");
        prompt.append(userMessage).append("\n\n");
        
        prompt.append("Hãy trả lời một cách chuyên nghiệp, thân thiện và hữu ích:");

        return prompt.toString();
    }

    /**
     * Fallback response khi AI service không available
     */
    private String buildFallbackResponse(List<DiseaseDTO> diseaseList, String userMessage) {
        if (diseaseList.isEmpty()) {
            return "Xin chào! Tôi là bác sĩ thú y AI. " +
                   "Tôi hiện chưa tìm thấy thông tin cụ thể về câu hỏi của bạn. " +
                   "Vui lòng mô tả chi tiết hơn về vấn đề của thú cưng, " +
                   "hoặc liên hệ trực tiếp với bác sĩ thú y để được tư vấn chính xác hơn.";
        }

        // Trả về disease đầu tiên
        DiseaseDTO firstDisease = diseaseList.get(0);
        StringBuilder response = new StringBuilder();
        
        response.append("Dựa trên câu hỏi của bạn, đây là thông tin liên quan:\n\n");
        response.append("**").append(firstDisease.getTitle()).append("**\n\n");
        response.append(firstDisease.getContent());
        
        if (diseaseList.size() > 1) {
            response.append("\n\n---\n");
            response.append("Lưu ý: Còn ").append(diseaseList.size() - 1)
                    .append(" thông tin bệnh liên quan khác. ");
        }
        
        response.append("\n\n⚠️ **Quan trọng:** Đây chỉ là thông tin tham khảo. ");
        response.append("Nếu thú cưng của bạn có triệu chứng nghiêm trọng, ");
        response.append("vui lòng đưa đến bác sĩ thú y ngay lập tức.");

        return response.toString();
    }
}

