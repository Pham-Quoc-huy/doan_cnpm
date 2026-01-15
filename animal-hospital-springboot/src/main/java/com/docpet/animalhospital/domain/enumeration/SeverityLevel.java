package com.docpet.animalhospital.domain.enumeration;

public enum SeverityLevel {
    MILD("MILD", "Nhẹ"),
    MODERATE("MODERATE", "Trung bình"),
    SEVERE("SEVERE", "Nghiêm trọng"),
    CRITICAL("CRITICAL", "Khẩn cấp"),
    GENERAL("GENERAL", "Thông tin chung");

    private final String value;
    private final String description;

    SeverityLevel(String value, String description) {
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





