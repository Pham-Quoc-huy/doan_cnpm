package com.docpet.animalhospital.service.dto;

import java.io.Serializable;

public class AssistantWithUserDTO extends AdminUserDTO implements Serializable {

    private Long assistantId;

    public AssistantWithUserDTO() {
        super();
    }

    public AssistantWithUserDTO(Long assistantId, com.docpet.animalhospital.domain.User user) {
        super(user);
        this.assistantId = assistantId;
    }

    public Long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Long assistantId) {
        this.assistantId = assistantId;
    }
}

