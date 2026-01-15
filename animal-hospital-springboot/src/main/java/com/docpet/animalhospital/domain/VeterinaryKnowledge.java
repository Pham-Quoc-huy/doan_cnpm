package com.docpet.animalhospital.domain;

import com.docpet.animalhospital.domain.enumeration.KnowledgeCategory;
import com.docpet.animalhospital.domain.enumeration.SeverityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "veterinary_knowledge")
public class VeterinaryKnowledge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private KnowledgeCategory category;

    @Size(max = 1000)
    @Column(name = "keywords", length = 1000)
    private String keywords; // Comma-separated keywords: "nôn,tiêu chảy,chó,mèo"

    @NotNull
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Size(max = 100)
    @Column(name = "species", length = 100)
    private String species; // "Chó", "Mèo", "Tất cả", etc.

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", length = 50)
    private SeverityLevel severityLevel;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    public VeterinaryKnowledge() {
        this.createdDate = Instant.now();
        this.lastModifiedDate = Instant.now();
        this.isActive = true;
    }

    public Long getId() {
        return this.id;
    }

    public VeterinaryKnowledge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VeterinaryKnowledge title(String title) {
        this.setTitle(title);
        return this;
    }

    public KnowledgeCategory getCategory() {
        return this.category;
    }

    public void setCategory(KnowledgeCategory category) {
        this.category = category;
    }

    public VeterinaryKnowledge category(KnowledgeCategory category) {
        this.setCategory(category);
        return this;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public VeterinaryKnowledge keywords(String keywords) {
        this.setKeywords(keywords);
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public VeterinaryKnowledge content(String content) {
        this.setContent(content);
        return this;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public VeterinaryKnowledge species(String species) {
        this.setSpecies(species);
        return this;
    }

    public SeverityLevel getSeverityLevel() {
        return this.severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public VeterinaryKnowledge severityLevel(SeverityLevel severityLevel) {
        this.setSeverityLevel(severityLevel);
        return this;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public VeterinaryKnowledge isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public VeterinaryKnowledge createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public VeterinaryKnowledge lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdDate == null) {
            this.createdDate = Instant.now();
        }
        if (this.lastModifiedDate == null) {
            this.lastModifiedDate = Instant.now();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VeterinaryKnowledge)) {
            return false;
        }
        return getId() != null && getId().equals(((VeterinaryKnowledge) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "VeterinaryKnowledge{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", category='" + getCategory() + "'" +
            ", species='" + getSpecies() + "'" +
            ", severityLevel='" + getSeverityLevel() + "'" +
            ", isActive=" + getIsActive() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}





