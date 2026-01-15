package com.docpet.animalhospital.service;

import java.util.List;

/**
 * Interface cho AI Service
 * Hỗ trợ nhiều provider (Gemini, OpenAI, etc.)
 */
public interface AIService {
    
    /**
     * Generate response từ AI model
     * @param prompt Prompt đầy đủ (bao gồm system prompt + context + user message)
     * @param context Danh sách context từ knowledge base (optional, có thể null)
     * @return Response từ AI
     * @throws Exception Nếu có lỗi khi gọi API
     */
    String generateResponse(String prompt, List<String> context) throws Exception;
    
    /**
     * Kiểm tra xem service có available không
     * @return true nếu service sẵn sàng
     */
    boolean isAvailable();
}

