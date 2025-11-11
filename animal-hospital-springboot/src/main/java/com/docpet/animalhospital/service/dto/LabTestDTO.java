package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class LabTestDTO implements Serializable {

    private Long id;
    private String testName;
    private String testType;
    private String description;
    private String status;
    private String result;
    private String notes;
    private ZonedDateTime requestedDate;
    private ZonedDateTime completedDate;
    private ZonedDateTime sampleCollectedDate;
    private Long appointmentId;
    private Long petId;
    private Long requestedById;
    private Long assignedToId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(ZonedDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public ZonedDateTime getSampleCollectedDate() {
        return sampleCollectedDate;
    }

    public void setSampleCollectedDate(ZonedDateTime sampleCollectedDate) {
        this.sampleCollectedDate = sampleCollectedDate;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getRequestedById() {
        return requestedById;
    }

    public void setRequestedById(Long requestedById) {
        this.requestedById = requestedById;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabTestDTO)) return false;
        LabTestDTO that = (LabTestDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

