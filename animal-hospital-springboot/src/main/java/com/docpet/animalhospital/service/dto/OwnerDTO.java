package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class OwnerDTO implements Serializable {

    private Long id;
    private String name;
    private String phone;
    private String address;
    private Long userId;

    public OwnerDTO() {}

    public OwnerDTO(com.docpet.animalhospital.domain.Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.phone = owner.getPhone();
        this.address = owner.getAddress();
        if (owner.getUser() != null) {
            this.userId = owner.getUser().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (!(o instanceof OwnerDTO)) return false;
        OwnerDTO ownerDTO = (OwnerDTO) o;
        return Objects.equals(id, ownerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

