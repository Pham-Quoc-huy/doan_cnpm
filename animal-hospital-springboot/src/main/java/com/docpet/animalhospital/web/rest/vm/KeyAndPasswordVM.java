package com.docpet.animalhospital.web.rest.vm;

import jakarta.validation.constraints.Size;

public class KeyAndPasswordVM {

    private String key;

    @Size(min = 4, max = 100)
    private String newPassword;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

