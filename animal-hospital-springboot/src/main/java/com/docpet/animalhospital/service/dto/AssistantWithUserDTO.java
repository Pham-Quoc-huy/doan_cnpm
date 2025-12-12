package com.docpet.animalhospital.service.dto;

import java.io.Serializable;

public class AssistantWithUserDTO extends AdminUserDTO implements Serializable {

    private Long assistantId;
    private Long userId; // Lưu userId riêng vì id sẽ là assistantId

    public AssistantWithUserDTO() {
        super();
    }

    public AssistantWithUserDTO(Long assistantId, com.docpet.animalhospital.domain.User user) {
        super(user);
        this.assistantId = assistantId;
        this.userId = user != null ? user.getId() : null;
        // Override id từ UserDTO để trả về assistantId thay vì userId
        super.setId(assistantId);
    }

    /**
     * Override getId() để trả về assistantId thay vì userId
     * Điều này đảm bảo khi client dùng id để xóa, nó sẽ dùng đúng assistantId
     */
    @Override
    public Long getId() {
        return assistantId;
    }

    /**
     * Override setId() để set assistantId
     */
    @Override
    public void setId(Long id) {
        this.assistantId = id;
        super.setId(id); // Cũng set vào super để giữ consistency
    }

    public Long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Long assistantId) {
        this.assistantId = assistantId;
        super.setId(assistantId); // Đồng bộ với id
    }

    /**
     * Get userId riêng (không phải assistantId)
     */
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
