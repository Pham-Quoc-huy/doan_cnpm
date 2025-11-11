package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.util.Set;

public class AdminUserDTO extends UserDTO implements Serializable {

    public AdminUserDTO() {
        super();
    }

    public AdminUserDTO(com.docpet.animalhospital.domain.User user) {
        super(user);
    }
}

