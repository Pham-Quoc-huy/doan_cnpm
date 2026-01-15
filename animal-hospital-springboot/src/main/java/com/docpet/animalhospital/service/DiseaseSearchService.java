package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.DiseaseCat;
import com.docpet.animalhospital.domain.DiseaseDog;
import com.docpet.animalhospital.repository.DiseaseCatRepository;
import com.docpet.animalhospital.repository.DiseaseDogRepository;
import com.docpet.animalhospital.service.dto.DiseaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service để search bệnh theo loài (Chó/Mèo)
 * Logic: Detect species → Map table → Search disease
 */
@Service
@Transactional(readOnly = true)
public class DiseaseSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(DiseaseSearchService.class);
    private static final int DEFAULT_SEARCH_LIMIT = 5;

    private final DiseaseDogRepository diseaseDogRepository;
    private final DiseaseCatRepository diseaseCatRepository;

    public DiseaseSearchService(
        DiseaseDogRepository diseaseDogRepository,
        DiseaseCatRepository diseaseCatRepository
    ) {
        this.diseaseDogRepository = diseaseDogRepository;
        this.diseaseCatRepository = diseaseCatRepository;
    }

    /**
     * Search bệnh dựa trên message và species
     * @param userMessage Câu hỏi của user
     * @param species "Chó" hoặc "Mèo"
     * @return Danh sách bệnh liên quan
     */
    public List<DiseaseDTO> searchDisease(String userMessage, String species) {
        LOG.info("Searching disease for message: '{}', species: {}", userMessage, species);

        if (species == null || species.isEmpty()) {
            LOG.warn("Species is null or empty, cannot search disease");
            return List.of();
        }

        // Extract keywords từ message (loại bỏ species name)
        List<String> keywords = extractKeywords(userMessage, species);
        LOG.info("Extracted keywords: {}", keywords);

        if (keywords.isEmpty()) {
            LOG.warn("No keywords extracted from message: {}", userMessage);
            return List.of();
        }

        // Map species → table và search
        List<DiseaseDTO> results;
        if ("Chó".equalsIgnoreCase(species) || "cho".equalsIgnoreCase(species)) {
            results = searchDiseaseDog(keywords);
        } else if ("Mèo".equalsIgnoreCase(species) || "meo".equalsIgnoreCase(species)) {
            results = searchDiseaseCat(keywords);
        } else {
            LOG.warn("Unknown species: {}", species);
            return List.of();
        }

        LOG.info("Found {} disease results", results.size());
        return results;
    }

    /**
     * Search trong table disease_dog
     */
    private List<DiseaseDTO> searchDiseaseDog(List<String> keywords) {
        LOG.info("Searching in disease_dog table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseDog> found = diseaseDogRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_dog", found.size(), keyword);

            for (DiseaseDog disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Chó");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                // Tính relevance score
                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        // Sort theo relevance score
        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_cat
     */
    private List<DiseaseDTO> searchDiseaseCat(List<String> keywords) {
        LOG.info("Searching in disease_cat table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseCat> found = diseaseCatRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_cat", found.size(), keyword);

            for (DiseaseCat disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Mèo");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                // Tính relevance score
                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        // Sort theo relevance score
        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Convert DiseaseDog to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseDog disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseCat to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseCat disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Extract keywords từ message, loại bỏ species name và stop words
     */
    private List<String> extractKeywords(String message, String species) {
        if (message == null || message.trim().isEmpty()) {
            return List.of();
        }

        // Stop words tiếng Việt
        List<String> stopWords = Arrays.asList(
            "của", "tôi", "bạn", "là", "và", "có", "không", "được", "một", "các",
            "với", "cho", "về", "này", "đó", "đã", "sẽ", "đang", "rất", "như",
            "thì", "mà", "nếu", "khi", "từ", "trong", "đến", "bị", "ở", "vì"
        );

        // Loại bỏ species name từ message
        String lowerMessage = message.toLowerCase();
        String lowerSpecies = species != null ? species.toLowerCase() : "";
        if (!lowerSpecies.isEmpty()) {
            lowerMessage = lowerMessage.replace(lowerSpecies, " ");
            // Loại bỏ các biến thể
            lowerMessage = lowerMessage.replace("chó", " ").replace("cho", " ");
            lowerMessage = lowerMessage.replace("mèo", " ").replace("meo", " ");
        }

        // Tách từ
        String[] words = lowerMessage
            .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
            .split("\\s+");

        List<String> keywords = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (word.length() > 1 && !stopWords.contains(word)) {
                keywords.add(word);
                String normalized = normalizeVietnamese(word);
                if (!normalized.equals(word)) {
                    keywords.add(normalized);
                }
            }
        }

        return keywords;
    }

    /**
     * Normalize tiếng Việt - loại bỏ dấu
     */
    private String normalizeVietnamese(String text) {
        if (text == null) return "";
        
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
}
