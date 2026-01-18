package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO cho Chat Response (Anonymous)
 */
public class ChatResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String response; // AI response

    private String sessionId; // Session ID để tiếp tục conversation (UUID)

    private Instant createdDate;

    public ChatResponseDTO() {
        this.createdDate = Instant.now();
    }

    public ChatResponseDTO(String response, String sessionId) {
        this.response = response;
        this.sessionId = sessionId;
        this.createdDate = Instant.now();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ChatResponseDTO{" +
            "response='" + response + '\'' +
            ", sessionId='" + sessionId + '\'' +
            ", createdDate=" + createdDate +
            '}';
    }
}

