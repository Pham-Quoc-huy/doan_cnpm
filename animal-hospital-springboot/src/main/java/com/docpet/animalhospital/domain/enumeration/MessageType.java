package com.docpet.animalhospital.domain.enumeration;

public enum MessageType {
    USER("USER", "Tin nhắn từ người dùng"),
    AI("AI", "Phản hồi từ AI");

    private final String value;
    private final String description;

    MessageType(String value, String description) {
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





