package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class VetDTO implements Serializable {

    private Long id;
    private String licenseNo;
    private String specialization;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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
        if (!(o instanceof VetDTO)) return false;
        VetDTO vetDTO = (VetDTO) o;
        return Objects.equals(id, vetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

