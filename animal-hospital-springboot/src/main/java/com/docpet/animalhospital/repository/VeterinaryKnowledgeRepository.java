package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.VeterinaryKnowledge;
import com.docpet.animalhospital.domain.enumeration.KnowledgeCategory;
import com.docpet.animalhospital.domain.enumeration.SeverityLevel;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaryKnowledgeRepository extends JpaRepository<VeterinaryKnowledge, Long> {

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "(vk.isActive = true OR vk.isActive IS NULL) AND (" +
           "LOWER(vk.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(vk.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(vk.content) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           ") ORDER BY " +
           "CASE WHEN LOWER(vk.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 ELSE 2 END, " +
           "CASE WHEN LOWER(vk.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 ELSE 2 END")
    List<VeterinaryKnowledge> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "(vk.isActive = true OR vk.isActive IS NULL) AND (" +
           "LOWER(vk.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(vk.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(vk.content) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           ") AND (vk.species = :species OR vk.species = 'Tất cả' OR vk.species IS NULL) " +
           "ORDER BY " +
           "CASE WHEN LOWER(vk.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 ELSE 2 END")
    List<VeterinaryKnowledge> searchByKeywordAndSpecies(
        @Param("keyword") String keyword,
        @Param("species") String species,
        Pageable pageable
    );

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "vk.isActive = true AND vk.category = :category " +
           "ORDER BY vk.createdDate DESC")
    List<VeterinaryKnowledge> findByCategory(
        @Param("category") KnowledgeCategory category,
        Pageable pageable
    );

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "vk.isActive = true AND vk.category = :category AND " +
           "(vk.species = :species OR vk.species = 'Tất cả' OR vk.species IS NULL) " +
           "ORDER BY vk.createdDate DESC")
    List<VeterinaryKnowledge> findByCategoryAndSpecies(
        @Param("category") KnowledgeCategory category,
        @Param("species") String species,
        Pageable pageable
    );

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "vk.isActive = true AND vk.severityLevel = :severityLevel " +
           "ORDER BY vk.createdDate DESC")
    List<VeterinaryKnowledge> findBySeverityLevel(
        @Param("severityLevel") SeverityLevel severityLevel,
        Pageable pageable
    );

    @Query("SELECT vk FROM VeterinaryKnowledge vk WHERE " +
           "vk.isActive = true " +
           "ORDER BY vk.createdDate DESC")
    List<VeterinaryKnowledge> findAllActive(Pageable pageable);
}





