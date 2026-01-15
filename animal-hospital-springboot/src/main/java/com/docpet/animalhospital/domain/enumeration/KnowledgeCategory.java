package com.docpet.animalhospital.domain.enumeration;

public enum KnowledgeCategory {
    DISEASE("DISEASE", "Bệnh lý"),
    NUTRITION("NUTRITION", "Dinh dưỡng"),
    CARE("CARE", "Chăm sóc"),
    BEHAVIOR("BEHAVIOR", "Hành vi"),
    EMERGENCY("EMERGENCY", "Sơ cứu"),
    VACCINATION("VACCINATION", "Tiêm phòng"),
    MEDICATION("MEDICATION", "Thuốc thú y"),
    PREVENTION("PREVENTION", "Phòng bệnh"),
    GENERAL("GENERAL", "Thông tin chung");

    private final String value;
    private final String description;

    KnowledgeCategory(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return value;
    }
}





