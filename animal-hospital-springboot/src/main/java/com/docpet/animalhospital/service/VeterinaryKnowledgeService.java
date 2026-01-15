package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.VeterinaryKnowledge;
import com.docpet.animalhospital.repository.VeterinaryKnowledgeRepository;
import com.docpet.animalhospital.service.dto.VeterinaryKnowledgeDTO;
import com.docpet.animalhospital.service.mapper.VeterinaryKnowledgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VeterinaryKnowledgeService {

    private static final Logger LOG = LoggerFactory.getLogger(VeterinaryKnowledgeService.class);
    private static final int DEFAULT_SEARCH_LIMIT = 5; // Top 5 results

    private final VeterinaryKnowledgeRepository veterinaryKnowledgeRepository;
    private final VeterinaryKnowledgeMapper veterinaryKnowledgeMapper;

    public VeterinaryKnowledgeService(
        VeterinaryKnowledgeRepository veterinaryKnowledgeRepository,
        VeterinaryKnowledgeMapper veterinaryKnowledgeMapper
    ) {
        this.veterinaryKnowledgeRepository = veterinaryKnowledgeRepository;
        this.veterinaryKnowledgeMapper = veterinaryKnowledgeMapper;
    }

    /**
     * Tìm kiếm knowledge liên quan đến câu hỏi của user
     * @param userMessage Câu hỏi của user
     * @param species Loài thú cưng (nếu có)
     * @return Danh sách knowledge liên quan (đã sort theo relevance)
     */
    public List<VeterinaryKnowledgeDTO> searchRelevantKnowledge(String userMessage, String species) {
        LOG.info("Searching knowledge for message: '{}', species: {}", userMessage, species);

        // Check database có data không
        long totalCount = veterinaryKnowledgeRepository.count();
        LOG.info("Total knowledge items in database: {}", totalCount);
        if (totalCount == 0) {
            LOG.warn("Database is empty! Please run CREATE_VETERINARY_KNOWLEDGE_TABLE.sql to seed data.");
        }

        // Extract keywords từ message
        List<String> keywords = extractKeywords(userMessage);
        LOG.info("Extracted keywords from '{}': {}", userMessage, keywords);

        if (keywords.isEmpty()) {
            LOG.warn("No keywords extracted from message: {}", userMessage);
            return List.of();
        }

        // Loại bỏ keyword quá phổ biến (chó, mèo) nếu có keyword khác quan trọng hơn
        List<String> filteredKeywords = filterCommonKeywords(keywords);
        LOG.info("Filtered keywords (removed common words): {}", filteredKeywords);

        // Nếu sau khi filter không còn keyword nào, dùng lại keywords gốc
        if (filteredKeywords.isEmpty()) {
            filteredKeywords = keywords;
        }

        // Search với tất cả keywords và tính relevance score
        Map<Long, VeterinaryKnowledgeDTO> knowledgeMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20); // Lấy nhiều hơn để tính score
        
        for (String keyword : filteredKeywords) {
            LOG.info("Searching with keyword: '{}', species: {}", keyword, species);
            List<VeterinaryKnowledge> found;
            
            if (species != null && !species.isEmpty()) {
                found = veterinaryKnowledgeRepository.searchByKeywordAndSpecies(keyword, species, pageable);
                LOG.info("Found {} items with keyword '{}' and species '{}'", found.size(), keyword, species);
            } else {
                found = veterinaryKnowledgeRepository.searchByKeyword(keyword, pageable);
                LOG.info("Found {} items with keyword '{}' (no species filter)", found.size(), keyword);
            }

            // Tính relevance score cho mỗi knowledge
            for (VeterinaryKnowledge knowledge : found) {
                VeterinaryKnowledgeDTO dto = veterinaryKnowledgeMapper.toDto(knowledge);
                Long id = dto.getId();
                
                // Add vào map nếu chưa có
                if (!knowledgeMap.containsKey(id)) {
                    knowledgeMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }
                
                // Tính score: title match = 3 điểm, keywords match = 2 điểm, content match = 1 điểm
                int score = relevanceScores.get(id);
                String lowerTitle = knowledge.getTitle() != null ? knowledge.getTitle().toLowerCase() : "";
                String lowerKeywords = knowledge.getKeywords() != null ? knowledge.getKeywords().toLowerCase() : "";
                String lowerContent = knowledge.getContent() != null ? knowledge.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();
                
                if (lowerTitle.contains(lowerKeyword)) {
                    score += 3;
                }
                if (lowerKeywords.contains(lowerKeyword)) {
                    score += 2;
                }
                if (lowerContent.contains(lowerKeyword)) {
                    score += 1;
                }
                
                relevanceScores.put(id, score);
                LOG.debug("Knowledge '{}' score: {}", knowledge.getTitle(), score);
            }
        }

        // Sort theo relevance score (cao nhất trước)
        List<VeterinaryKnowledgeDTO> results = knowledgeMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA); // Descending order
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());

        LOG.info("Found {} relevant knowledge items (sorted by relevance)", results.size());
        if (!results.isEmpty()) {
            LOG.info("Top result: '{}' (score: {})", 
                results.get(0).getTitle(), 
                relevanceScores.get(results.get(0).getId()));
        }
        
        return results;
    }

    /**
     * Loại bỏ keyword quá phổ biến (chó, mèo) nếu có keyword khác quan trọng hơn
     * Ví dụ: ["chó", "nôn"] -> ["nôn"] (vì "chó" quá phổ biến)
     */
    private List<String> filterCommonKeywords(List<String> keywords) {
        // Common words (species names) - quá phổ biến, không nên dùng để search
        List<String> commonWords = Arrays.asList("chó", "mèo", "cho", "meo", "dog", "cat", "cún", "cẩu");
        
        // Nếu có keyword khác ngoài common words, loại bỏ common words
        boolean hasNonCommonKeyword = keywords.stream()
            .anyMatch(kw -> !commonWords.contains(kw.toLowerCase()));
        
        if (hasNonCommonKeyword) {
            return keywords.stream()
                .filter(kw -> !commonWords.contains(kw.toLowerCase()))
                .collect(Collectors.toList());
        }
        
        // Nếu chỉ có common words, giữ lại
        return keywords;
    }

    /**
     * Extract keywords từ message
     * Loại bỏ stop words và lấy các từ quan trọng
     */
    private List<String> extractKeywords(String message) {
        if (message == null || message.trim().isEmpty()) {
            return List.of();
        }

        // Stop words tiếng Việt (các từ không quan trọng)
        List<String> stopWords = Arrays.asList(
            "của", "tôi", "bạn", "là", "và", "có", "không", "được", "một", "các",
            "với", "cho", "về", "này", "đó", "đã", "sẽ", "đang", "rất", "như",
            "thì", "mà", "nếu", "khi", "từ", "trong", "đến", "bị", "ở", "vì"
        );

        // Tách từ từ message gốc (giữ nguyên dấu)
        String[] words = message.toLowerCase()
            .replaceAll("[^\\p{L}\\p{N}\\s]", " ") // Loại bỏ ký tự đặc biệt
            .split("\\s+");

        List<String> keywords = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (word.length() > 1 && !stopWords.contains(word)) {
                // Thêm cả từ gốc (có dấu) và normalized (không dấu) để search tốt hơn
                keywords.add(word); // Từ gốc: "nôn"
                String normalized = normalizeVietnamese(word);
                if (!normalized.equals(word)) {
                    keywords.add(normalized); // Normalized: "non"
                }
            }
        }

        return keywords;
    }

    /**
     * Normalize tiếng Việt - loại bỏ dấu để search tốt hơn
     * Ví dụ: "nôn" và "non" đều match
     */
    private String normalizeVietnamese(String text) {
        if (text == null) return "";
        
        // Loại bỏ dấu tiếng Việt
        return text
            .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
            .replaceAll("[èéẹẻẽêềếệểễ]", "e")
            .replaceAll("[ìíịỉĩ]", "i")
            .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
            .replaceAll("[ùúụủũưừứựửữ]", "u")
            .replaceAll("[ỳýỵỷỹ]", "y")
            .replaceAll("[đ]", "d")
            .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
            .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
            .replaceAll("[ÌÍỊỈĨ]", "I")
            .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
            .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
            .replaceAll("[ỲÝỴỶỸ]", "Y")
            .replaceAll("[Đ]", "D");
    }

    /**
     * Tìm kiếm theo category và species
     */
    public List<VeterinaryKnowledgeDTO> findByCategoryAndSpecies(
        com.docpet.animalhospital.domain.enumeration.KnowledgeCategory category,
        String species
    ) {
        LOG.debug("Finding knowledge by category: {}, species: {}", category, species);
        Pageable pageable = PageRequest.of(0, 10);
        
        List<VeterinaryKnowledge> knowledge;
        if (species != null && !species.isEmpty()) {
            knowledge = veterinaryKnowledgeRepository.findByCategoryAndSpecies(category, species, pageable);
        } else {
            knowledge = veterinaryKnowledgeRepository.findByCategory(category, pageable);
        }

        return knowledge.stream()
            .map(veterinaryKnowledgeMapper::toDto)
            .collect(Collectors.toList());
    }
}





