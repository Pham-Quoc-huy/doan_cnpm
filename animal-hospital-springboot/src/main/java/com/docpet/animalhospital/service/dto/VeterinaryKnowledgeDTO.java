package com.docpet.animalhospital.service.dto;

import com.docpet.animalhospital.domain.enumeration.KnowledgeCategory;
import com.docpet.animalhospital.domain.enumeration.SeverityLevel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class VeterinaryKnowledgeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private KnowledgeCategory category;
    private String keywords;
    private String content;
    private String species;
    private SeverityLevel severityLevel;
    private Boolean isActive;
    private Instant createdDate;
    private Instant lastModifiedDate;

    public VeterinaryKnowledgeDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public KnowledgeCategory getCategory() {
        return category;
    }

    public void setCategory(KnowledgeCategory category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VeterinaryKnowledgeDTO)) return false;
        VeterinaryKnowledgeDTO that = (VeterinaryKnowledgeDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VeterinaryKnowledgeDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", category=" + category +
            ", species='" + species + '\'' +
            ", severityLevel=" + severityLevel +
            '}';
    }
}





