package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentResource.class);
    private static final String ENTITY_NAME = "appointment";

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentActionService appointmentActionService;
    private final UserRepository userRepository;

    public AppointmentResource(
        AppointmentService appointmentService,
        AppointmentRepository appointmentRepository,
        AppointmentActionService appointmentActionService,
        UserRepository userRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentActionService = appointmentActionService;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) throws URISyntaxException {
        LOG.debug("REST request to save Appointment : {}", appointmentDTO);
        if (appointmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new appointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        appointmentDTO = appointmentService.createAppointment(appointmentDTO, currentUserLogin);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/appointments/" + appointmentDTO.getId()));
        return ResponseEntity.created(new URI("/api/appointments/" + appointmentDTO.getId()))
            .headers(headers)
            .body(appointmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppointmentDTO appointmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appointment : {}, {}", id, appointmentDTO);
        if (appointmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appointmentDTO = appointmentService.update(appointmentDTO);
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppointmentDTO> partialUpdateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppointmentDTO appointmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appointment partially : {}, {}", id, appointmentDTO);
        if (appointmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppointmentDTO> result = appointmentService.partialUpdate(appointmentDTO);
        return result.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        LOG.debug("REST request to get all Appointments for current user");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.findAllForCurrentOwner(currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appointment : {}", id);
        Optional<AppointmentDTO> appointmentDTO = appointmentService.findOne(id);
        return appointmentDTO.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appointment : {}", id);
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vet")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get all appointments for date: {} for vet", date);
        List<AppointmentDTO> appointments = appointmentService.getAllAppointmentsByDateForVet(date);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/pet/{petId}/history")
    public ResponseEntity<List<AppointmentDTO>> getPetAppointmentHistory(@PathVariable("petId") Long petId) {
        LOG.debug("REST request to get appointment history for pet: {}", petId);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getPetAppointmentHistory(petId, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<AppointmentDTO> updateAppointmentStatus(
        @PathVariable("id") Long id,
        @RequestBody String status
    ) {
        LOG.debug("REST request to update appointment status: {} for appointment: {}", status, id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, status, currentUserLogin);
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @GetMapping("/emergency")
    public ResponseEntity<List<AppointmentDTO>> getEmergencyAppointmentsByDate(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get emergency appointments for date: {}", date);
        List<AppointmentDTO> appointments = appointmentService.getEmergencyAppointmentsByDate(date);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/regular")
    public ResponseEntity<List<AppointmentDTO>> getRegularAppointmentsByDate(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get regular appointments for date: {}", date);
        List<AppointmentDTO> appointments = appointmentService.getRegularAppointmentsByDate(date);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/vet/emergency")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getEmergencyAppointmentsByDateForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get emergency appointments for date: {} for current vet", date);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getEmergencyAppointmentsByDateForVet(date, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/vet/regular")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getRegularAppointmentsByDateForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get regular appointments for date: {} for current vet", date);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getRegularAppointmentsByDateForVet(date, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/vet/available")
    public ResponseEntity<Boolean> checkVetAvailability(
        @RequestParam("vetId") Long vetId,
        @RequestParam("startTime") String startTimeString,
        @RequestParam(value = "endTime", required = false) String endTimeString) {
        LOG.debug("REST request to check availability for vet: {} at startTime: {}", vetId, startTimeString);
        
        try {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
            java.time.ZonedDateTime startTime = java.time.ZonedDateTime.parse(startTimeString, formatter);
            java.time.ZonedDateTime endTime = null;
            
            if (endTimeString != null && !endTimeString.trim().isEmpty()) {
                endTime = java.time.ZonedDateTime.parse(endTimeString, formatter);
            }
            
            boolean isAvailable = appointmentService.checkVetAvailability(vetId, startTime, endTime);
            return ResponseEntity.ok().body(isAvailable);
        } catch (java.time.format.DateTimeParseException e) {
            throw new BadRequestAlertException("Invalid date format. Expected ISO 8601 format (e.g., 2024-01-20T10:00:00+07:00)", ENTITY_NAME, "invaliddate");
        }
    }

    @GetMapping("/assistant/assigned")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyAssignedAppointments(
        @RequestParam(value = "status", required = false) String status
    ) {
        LOG.debug("REST request to get assigned appointments for current assistant with status: {}", status);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Long currentUserId = userRepository.findOneByLogin(currentUserLogin)
            .map(user -> user.getId())
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        List<AppointmentActionDTO> actions;
        if (status != null && !status.trim().isEmpty()) {
            actions = appointmentActionService.findByAssignedToIdAndStatus(currentUserId, status);
        } else {
            actions = appointmentActionService.findByAssignedToId(currentUserId);
        }

        return ResponseEntity.ok().body(actions);
    }

    @GetMapping("/assistant/assigned/pending")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyPendingAssignments() {
        LOG.debug("REST request to get pending assigned appointments for current assistant");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Long currentUserId = userRepository.findOneByLogin(currentUserLogin)
            .map(user -> user.getId())
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        List<AppointmentActionDTO> actions = appointmentActionService.findByAssignedToIdAndStatus(
            currentUserId,
            "PENDING"
        );

        return ResponseEntity.ok().body(actions);
    }
}

