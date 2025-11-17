package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class AssistantDTO implements Serializable {

    private Long id;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssistantDTO)) return false;
        AssistantDTO assistantDTO = (AssistantDTO) o;
        return Objects.equals(id, assistantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

