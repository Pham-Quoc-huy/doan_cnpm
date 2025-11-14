package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.AppointmentAction;
import com.docpet.animalhospital.repository.AppointmentActionRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.LabTestService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.LabTestDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vet")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetWorkflowResource {

    private static final Logger LOG = LoggerFactory.getLogger(VetWorkflowResource.class);
    private static final String ENTITY_NAME = "appointment";

    private final AppointmentService appointmentService;
    private final AppointmentActionService appointmentActionService;
    private final LabTestService labTestService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentActionRepository appointmentActionRepository;
    private final UserRepository userRepository;

    public VetWorkflowResource(
        AppointmentService appointmentService,
        AppointmentActionService appointmentActionService,
        LabTestService labTestService,
        AppointmentRepository appointmentRepository,
        AppointmentActionRepository appointmentActionRepository,
        UserRepository userRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentActionService = appointmentActionService;
        this.labTestService = labTestService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentActionRepository = appointmentActionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/appointments/{id}/detail")
    public ResponseEntity<AppointmentDTO> getAppointmentDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get appointment detail for vet workflow: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Optional<AppointmentDTO> appointmentDTO = appointmentService.findOne(id);
        if (appointmentDTO.isPresent()) {
            Appointment appointment = appointmentRepository.findById(id).orElse(null);
            if (appointment != null && appointment.getVet() != null && 
                appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
                AppointmentDTO dto = appointmentDTO.get();
                dto.setVet(null); // Không hiển thị thông tin vet trong response
                return ResponseEntity.ok().body(dto);
            } else {
                throw new BadRequestAlertException("You can only view your own appointments", ENTITY_NAME, "notauthorized");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/appointments/{id}/approve")
    public ResponseEntity<AppointmentDTO> approveAppointment(
        @PathVariable("id") Long id,
        @RequestBody(required = false) String notes) {
        LOG.debug("REST request to approve appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "APPROVED", currentUserLogin);
        appointmentActionService.createAppointmentAction(id, "APPROVE", "COMPLETED", "Appointment approved", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @PostMapping("/appointments/{id}/reject")
    public ResponseEntity<AppointmentDTO> rejectAppointment(
        @PathVariable("id") Long id,
        @RequestBody String notes) {
        LOG.debug("REST request to reject appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "REJECTED", currentUserLogin);
        appointmentActionService.createAppointmentAction(id, "REJECT", "COMPLETED", "Appointment rejected", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @PostMapping("/appointments/{id}/reschedule")
    public ResponseEntity<AppointmentDTO> rescheduleAppointment(
        @PathVariable("id") Long id,
        @Valid @RequestBody RescheduleRequest rescheduleRequest) {
        LOG.debug("REST request to reschedule appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        appointmentDTO.setTimeStart(rescheduleRequest.getNewTimeStart());
        appointmentDTO.setTimeEnd(rescheduleRequest.getNewTimeEnd());
        appointmentDTO.setStatus("RESCHEDULED");
        
        AppointmentDTO updatedAppointment = appointmentService.update(appointmentDTO);
        appointmentActionService.createAppointmentAction(id, "RESCHEDULE", "COMPLETED", "Appointment rescheduled", rescheduleRequest.getNotes(), currentUserLogin, null);
        
        return ResponseEntity.ok().body(updatedAppointment);
    }

    @PostMapping("/appointments/{id}/assign-assistant")
    public ResponseEntity<AppointmentActionDTO> assignAssistant(
        @PathVariable("id") Long id,
        @Valid @RequestBody AssignAssistantRequest assignRequest) {
        LOG.debug("REST request to assign assistant for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Lấy appointment để kiểm tra timeStart
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));

        // Kiểm tra appointment thuộc về vet hiện tại
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only assign assistant for your own appointments", ENTITY_NAME, "notauthorized");
        }

        // Lấy assistant user
        com.docpet.animalhospital.domain.User assistant = userRepository.findOneByLogin(assignRequest.getAssistantLogin())
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "assistantnotfound"));

        // Kiểm tra conflict: timeStart không được trùng nhau và phải cách nhau ít nhất 1 tiếng
        List<AppointmentAction> existingAssignments = appointmentActionRepository
            .findActiveAssignmentsByAssistantAndDate(assistant.getId(), appointment.getTimeStart());

        for (AppointmentAction existingAction : existingAssignments) {
            Appointment existingAppointment = existingAction.getAppointment();
            // Bỏ qua appointment hiện tại đang được phân công
            if (existingAppointment.getId().equals(id)) {
                continue;
            }

            ZonedDateTime existingTimeStart = existingAppointment.getTimeStart();
            ZonedDateTime newTimeStart = appointment.getTimeStart();

            // Tính khoảng cách giữa 2 timeStart
            long hoursDiff = Math.abs(java.time.Duration.between(existingTimeStart, newTimeStart).toHours());

            // Kiểm tra: trùng giờ (cùng hour) hoặc cách nhau < 1 tiếng
            if (existingTimeStart.getHour() == newTimeStart.getHour() || hoursDiff < 1) {
                throw new BadRequestAlertException(
                    "Trợ lý đã được phân công cho lịch hẹn khác vào thời gian này. TimeStart không được trùng nhau và phải cách nhau ít nhất 1 tiếng.",
                    ENTITY_NAME,
                    "assistantnotavailable"
                );
            }
        }

        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "ASSIGN_ASSISTANT", 
            "PENDING", 
            "Assistant assigned for sample collection", 
            assignRequest.getNotes(), 
            currentUserLogin,
            assignRequest.getAssistantLogin()
        );
        
        return ResponseEntity.ok().body(actionDTO);
    }

    @PostMapping("/appointments/{id}/request-home-visit")
    public ResponseEntity<AppointmentActionDTO> requestHomeVisit(
        @PathVariable("id") Long id,
        @Valid @RequestBody HomeVisitRequest homeVisitRequest) {
        LOG.debug("REST request to request home visit for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        appointmentDTO.setLocationType("AT_HOME");
        appointmentService.update(appointmentDTO);

        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "REQUEST_HOME_VISIT", 
            "PENDING", 
            "Home visit requested", 
            homeVisitRequest.getNotes(), 
            currentUserLogin,
            null
        );
        
        return ResponseEntity.ok().body(actionDTO);
    }

    @PostMapping("/appointments/{id}/request-lab-test")
    public ResponseEntity<LabTestDTO> requestLabTest(
        @PathVariable("id") Long id,
        @Valid @RequestBody LabTestRequest labTestRequest) {
        LOG.debug("REST request to request lab test for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        LabTestDTO labTestDTO = labTestService.createLabTest(
            id, 
            labTestRequest.getTestName(), 
            labTestRequest.getTestType(), 
            labTestRequest.getDescription(), 
            currentUserLogin,
            null
        );
        
        return ResponseEntity.ok().body(labTestDTO);
    }

    @GetMapping("/appointments/{id}/actions")
    public ResponseEntity<List<AppointmentActionDTO>> getAppointmentActions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get actions for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only view actions for your own appointments", ENTITY_NAME, "notauthorized");
        }

        List<AppointmentActionDTO> actions = appointmentActionService.findByAppointmentId(id);
        return ResponseEntity.ok().body(actions);
    }

    @GetMapping("/appointments/{id}/lab-tests")
    public ResponseEntity<List<LabTestDTO>> getAppointmentLabTests(@PathVariable("id") Long id) {
        LOG.debug("REST request to get lab tests for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only view lab tests for your own appointments", ENTITY_NAME, "notauthorized");
        }

        List<LabTestDTO> labTests = labTestService.findByAppointmentId(id);
        return ResponseEntity.ok().body(labTests);
    }

    // Request DTOs
    public static class RescheduleRequest {
        private ZonedDateTime newTimeStart;
        private ZonedDateTime newTimeEnd;
        private String notes;

        public ZonedDateTime getNewTimeStart() { return newTimeStart; }
        public void setNewTimeStart(ZonedDateTime newTimeStart) { this.newTimeStart = newTimeStart; }
        public ZonedDateTime getNewTimeEnd() { return newTimeEnd; }
        public void setNewTimeEnd(ZonedDateTime newTimeEnd) { this.newTimeEnd = newTimeEnd; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class AssignAssistantRequest {
        private String assistantLogin;
        private String notes;

        public String getAssistantLogin() { return assistantLogin; }
        public void setAssistantLogin(String assistantLogin) { this.assistantLogin = assistantLogin; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class HomeVisitRequest {
        private String notes;

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class LabTestRequest {
        private String testName;
        private String testType;
        private String description;

        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }
        public String getTestType() { return testType; }
        public void setTestType(String testType) { this.testType = testType; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}

