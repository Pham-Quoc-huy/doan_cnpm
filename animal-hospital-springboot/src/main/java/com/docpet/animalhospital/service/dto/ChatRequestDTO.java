package com.docpet.animalhospital.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * DTO cho Chat Request (Anonymous)
 */
public class ChatRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Message không được để trống")
    @Size(max = 2000, message = "Message không được vượt quá 2000 ký tự")
    private String message;

    private String sessionId; // Optional, để tiếp tục conversation

    private Long petId; // Optional, nếu user muốn chat về thú cưng cụ thể

    private List<String> conversationHistory; // Optional, lịch sử conversation (frontend gửi lên)

    public ChatRequestDTO() {}

    public ChatRequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public List<String> getConversationHistory() {
        return conversationHistory;
    }

    public void setConversationHistory(List<String> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    @Override
    public String toString() {
        return "ChatRequestDTO{" +
            "message='" + message + '\'' +
            ", sessionId='" + sessionId + '\'' +
            ", petId=" + petId +
            '}';
    }
}

