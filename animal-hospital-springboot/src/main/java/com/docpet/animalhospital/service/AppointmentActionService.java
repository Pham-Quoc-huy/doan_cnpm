package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.AppointmentAction;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.AppointmentActionRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentActionService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentActionService.class);

    private final AppointmentActionRepository appointmentActionRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentActionService(
        AppointmentActionRepository appointmentActionRepository,
        AppointmentRepository appointmentRepository,
        UserRepository userRepository
    ) {
        this.appointmentActionRepository = appointmentActionRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public AppointmentActionDTO createAppointmentAction(
        Long appointmentId,
        String actionType,
        String status,
        String description,
        String notes,
        String createdByLogin,
        String assignedToLogin
    ) {
        LOG.debug("Request to create AppointmentAction for appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointmentAction", "notfound"));

        User createdBy = userRepository.findOneByLogin(createdByLogin)
            .orElseThrow(() -> new BadRequestAlertException("User not found", "appointmentAction", "usernotfound"));

        AppointmentAction action = new AppointmentAction();
        action.setAppointment(appointment);
        action.setActionType(actionType);
        action.setStatus(status);
        action.setDescription(description);
        action.setNotes(notes);
        action.setCreatedBy(createdBy);
        action.setScheduledTime(ZonedDateTime.now());

        if (assignedToLogin != null && !assignedToLogin.trim().isEmpty()) {
            User assignedTo = userRepository.findOneByLogin(assignedToLogin)
                .orElseThrow(() -> new BadRequestAlertException("Assigned user not found", "appointmentAction", "assignednotfound"));
            action.setAssignedTo(assignedTo);
        }

        action = appointmentActionRepository.save(action);
        return toDto(action);
    }

    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAppointmentId(Long appointmentId) {
        LOG.debug("Request to get AppointmentActions for appointment: {}", appointmentId);
        return appointmentActionRepository.findByAppointment_Id(appointmentId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToId(Long assignedToId) {
        LOG.debug("Request to get AppointmentActions for assigned user: {}", assignedToId);
        return appointmentActionRepository.findByAssignedTo_Id(assignedToId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToIdAndStatus(Long assignedToId, String status) {
        LOG.debug("Request to get AppointmentActions for assigned user: {} with status: {}", assignedToId, status);
        return appointmentActionRepository.findByAssignedTo_IdAndStatus(assignedToId, status).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private AppointmentActionDTO toDto(AppointmentAction action) {
        AppointmentActionDTO dto = new AppointmentActionDTO();
        dto.setId(action.getId());
        dto.setActionType(action.getActionType());
        dto.setStatus(action.getStatus());
        dto.setDescription(action.getDescription());
        dto.setNotes(action.getNotes());
        dto.setScheduledTime(action.getScheduledTime());
        dto.setCompletedTime(action.getCompletedTime());
        if (action.getAppointment() != null) {
            dto.setAppointmentId(action.getAppointment().getId());
        }
        if (action.getAssignedTo() != null) {
            dto.setAssignedToId(action.getAssignedTo().getId());
        }
        if (action.getCreatedBy() != null) {
            dto.setCreatedById(action.getCreatedBy().getId());
        }
        return dto;
    }
}

