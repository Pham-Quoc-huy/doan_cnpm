package com.docpet.animalhospital.service;

import com.docpet.animalhospital.config.AiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiAIService implements AIService {

    private static final Logger LOG = LoggerFactory.getLogger(GeminiAIService.class);
    // API URL - Dùng v1beta (đã confirm có models trong v1beta)
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";
    
    private final AiProperties aiProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiAIService(AiProperties aiProperties, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.aiProperties = aiProperties;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateResponse(String prompt, List<String> context) throws Exception {
        if (!isAvailable()) {
            throw new IllegalStateException("AI service is not enabled or configured");
        }

        LOG.info("Generating response with Gemini API, model: {}", aiProperties.getModel());
        LOG.debug("API Key configured: {}", aiProperties.getApiKey() != null && !aiProperties.getApiKey().isEmpty());

        try {
            // Build request body
            Map<String, Object> requestBody = new HashMap<>();
            
            // Contents array
            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(Map.of("text", prompt)));
            requestBody.put("contents", List.of(content));
            
            // Generation config
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("temperature", aiProperties.getTemperature());
            generationConfig.put("maxOutputTokens", aiProperties.getMaxTokens());
            requestBody.put("generationConfig", generationConfig);

            // Build URL
            String url = String.format(GEMINI_API_URL, aiProperties.getModel(), aiProperties.getApiKey());
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            // Make API call
            LOG.info("Calling Gemini API, model: {}, URL: {}", aiProperties.getModel(), 
                url.replace(aiProperties.getApiKey(), "***"));
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
            );

            LOG.debug("Gemini API response status: {}", response.getStatusCode());
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                LOG.debug("Gemini API response body length: {}", response.getBody().length());
                return parseGeminiResponse(response.getBody());
            } else {
                String errorMsg = String.format("Gemini API returned error: %s, Body: %s", 
                    response.getStatusCode(), 
                    response.getBody());
                LOG.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

        } catch (RestClientException e) {
            LOG.error("Error calling Gemini API: {}", e.getMessage(), e);
            if (e.getMessage() != null && e.getMessage().contains("401")) {
                LOG.error("API Key might be invalid or expired. Please check your GEMINI_API_KEY.");
            } else if (e.getMessage() != null && e.getMessage().contains("404")) {
                LOG.error("Model '{}' might not exist. Please check model name.", aiProperties.getModel());
            } else if (e.getMessage() != null && e.getMessage().contains("429")) {
                LOG.error("Rate limit exceeded. Please wait a few minutes before trying again. " +
                         "Free tier limits: ~15 requests/minute or 1500 requests/day.");
            }
            throw new Exception("Failed to generate AI response: " + e.getMessage(), e);
        } catch (Exception e) {
            LOG.error("Unexpected error in GeminiAIService: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Parse response từ Gemini API
     */
    private String parseGeminiResponse(String responseBody) throws Exception {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode firstCandidate = candidates.get(0);
                JsonNode content = firstCandidate.path("content");
                JsonNode parts = content.path("parts");
                
                if (parts.isArray() && parts.size() > 0) {
                    JsonNode text = parts.get(0).path("text");
                    if (text.isTextual()) {
                        return text.asText();
                    }
                }
            }
            
            throw new Exception("Invalid response format from Gemini API");
        } catch (Exception e) {
            LOG.error("Error parsing Gemini response", e);
            throw new Exception("Failed to parse AI response: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isAvailable() {
        boolean available = aiProperties.isEnabled() 
            && aiProperties.getApiKey() != null 
            && !aiProperties.getApiKey().isEmpty()
            && "gemini".equalsIgnoreCase(aiProperties.getProvider());
        
        if (!available) {
            LOG.warn("AI Service not available - enabled: {}, hasApiKey: {}, provider: {}", 
                aiProperties.isEnabled(),
                aiProperties.getApiKey() != null && !aiProperties.getApiKey().isEmpty(),
                aiProperties.getProvider());
        }
        
        return available;
    }
}

