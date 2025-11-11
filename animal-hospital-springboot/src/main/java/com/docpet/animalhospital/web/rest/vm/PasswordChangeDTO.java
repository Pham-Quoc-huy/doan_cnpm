package com.docpet.animalhospital.web.rest.vm;

import jakarta.validation.constraints.Size;

public class PasswordChangeDTO {

    @Size(min = 4, max = 100)
    private String currentPassword;

    @Size(min = 4, max = 100)
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

