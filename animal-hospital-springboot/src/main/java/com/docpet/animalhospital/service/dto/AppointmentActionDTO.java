package com.docpet.animalhospital.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class AppointmentActionDTO implements Serializable {

    private Long id;
    private String actionType;
    private String status;
    private String description;
    private String notes;
    private ZonedDateTime scheduledTime;
    private ZonedDateTime completedTime;
    private Long appointmentId;
    private Long assignedToId;
    private Long createdById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(ZonedDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public ZonedDateTime getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(ZonedDateTime completedTime) {
        this.completedTime = completedTime;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentActionDTO)) return false;
        AppointmentActionDTO that = (AppointmentActionDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

