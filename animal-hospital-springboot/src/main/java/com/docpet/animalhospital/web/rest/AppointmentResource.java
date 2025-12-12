package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.AppointmentAssistant;
import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.repository.AppointmentAssistantRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.AppointmentMessageService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentMessageDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import com.docpet.animalhospital.web.rest.vm.SendMessageVM;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
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
    private final AppointmentMessageService appointmentMessageService;
    private final AppointmentAssistantRepository appointmentAssistantRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final AssistantRepository assistantRepository;

    public AppointmentResource(
        AppointmentService appointmentService,
        AppointmentRepository appointmentRepository,
        AppointmentActionService appointmentActionService,
        AppointmentMessageService appointmentMessageService,
        AppointmentAssistantRepository appointmentAssistantRepository,
        UserRepository userRepository,
        OwnerRepository ownerRepository,
        AssistantRepository assistantRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentActionService = appointmentActionService;
        this.appointmentMessageService = appointmentMessageService;
        this.appointmentAssistantRepository = appointmentAssistantRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.assistantRepository = assistantRepository;
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
        
        List<AppointmentDTO> appointments;
        
        // Kiểm tra xem user là owner, vet hay assistant
        boolean isOwner = ownerRepository.findByUser_Login(currentUserLogin).isPresent();
        boolean isVet = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.DOCTOR);
        boolean isAssistant = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ASSISTANT);
        
        LOG.debug("User roles - isOwner: {}, isVet: {}, isAssistant: {}", isOwner, isVet, isAssistant);
        
        // Collect appointments từ các role khác nhau
        java.util.Set<Long> appointmentIds = new java.util.HashSet<>();
        List<AppointmentDTO> allAppointments = new java.util.ArrayList<>();
        
        // Lấy appointments của owner (nếu có)
        if (isOwner) {
            List<AppointmentDTO> ownerAppointments = appointmentService.findAllForCurrentOwner(currentUserLogin);
            LOG.debug("Found {} owner appointments", ownerAppointments.size());
            for (AppointmentDTO apt : ownerAppointments) {
                if (appointmentIds.add(apt.getId())) {
                    allAppointments.add(apt);
                }
            }
        }
        
        // Lấy appointments của vet (nếu có)
        if (isVet) {
            List<AppointmentDTO> vetAppointments = appointmentService.findAllForCurrentVet(currentUserLogin);
            LOG.debug("Found {} vet appointments", vetAppointments.size());
            for (AppointmentDTO apt : vetAppointments) {
                if (appointmentIds.add(apt.getId())) {
                    allAppointments.add(apt);
                }
            }
        }
        
        // Lấy appointments của assistant (nếu có)
        if (isAssistant) {
            try {
                Optional<Assistant> assistantOpt = assistantRepository.findByUser_Login(currentUserLogin);
                if (assistantOpt.isPresent()) {
                    Assistant assistant = assistantOpt.get();
                    Long assistantId = assistant.getId();
                    LOG.debug("Found assistant with id: {}", assistantId);
                    
                    // Lấy appointments đã được phân công từ bảng appointment_assistant
                    List<AppointmentAssistant> appointmentAssistants = appointmentAssistantRepository
                        .findByAssistantIdWithRelations(assistantId);
                    
                    LOG.debug("Found {} appointment_assistant records for assistant id: {}", 
                        appointmentAssistants.size(), assistantId);
                    
                    // Convert sang AppointmentDTO
                    for (AppointmentAssistant aa : appointmentAssistants) {
                        try {
                            Long appointmentId = aa.getAppointment().getId();
                            Optional<AppointmentDTO> appointmentOpt = appointmentService.findOne(appointmentId);
                            if (appointmentOpt.isPresent() && appointmentIds.add(appointmentId)) {
                                allAppointments.add(appointmentOpt.get());
                            }
                        } catch (Exception e) {
                            LOG.warn("Error converting AppointmentAssistant to AppointmentDTO for appointment id: {}", 
                                aa.getAppointment() != null ? aa.getAppointment().getId() : "null", e);
                        }
                    }
                } else {
                    LOG.debug("User has ASSISTANT role but no assistant record found");
                }
            } catch (Exception e) {
                LOG.error("Error getting assistant appointments for user: {}", currentUserLogin, e);
            }
        }
        
        appointments = allAppointments;
        LOG.debug("Returning {} total appointments (merged from all roles)", appointments.size());
        
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

    @GetMapping("/regular")
    public ResponseEntity<List<AppointmentDTO>> getRegularAppointmentsByDateAndVetId(
        @RequestParam("date") String date,
        @RequestParam("vetId") Long vetId) {
        LOG.debug("REST request to get regular appointments for date: {} and vetId: {}", date, vetId);
        List<AppointmentDTO> appointments = appointmentService.getRegularAppointmentsByDateAndVetId(date, vetId);
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/emergency")
    public ResponseEntity<List<AppointmentDTO>> getEmergencyAppointmentsByDateAndVetId(
        @RequestParam("date") String date,
        @RequestParam("vetId") Long vetId) {
        LOG.debug("REST request to get emergency appointments for date: {} and vetId: {}", date, vetId);
        List<AppointmentDTO> appointments = appointmentService.getEmergencyAppointmentsByDateAndVetId(date, vetId);
        return ResponseEntity.ok().body(appointments);
    }

    @PostMapping("/vet/available")
    public ResponseEntity<Boolean> checkVetAvailability(@Valid @RequestBody CheckAvailabilityRequest request) {
        LOG.debug("REST request to check availability for vet: {} at startTime: {}", request.getVetId(), request.getStartTime());
        
        // Nếu không có endTime, tự động tính từ startTime + 60 phút
        java.time.ZonedDateTime endTime = request.getEndTime();
        if (endTime == null) {
            endTime = request.getStartTime().plusHours(1);
            LOG.debug("Auto-calculated endTime: {} (startTime + 60 minutes)", endTime);
        }
        
        boolean isAvailable = appointmentService.checkVetAvailability(
            request.getVetId(), 
            request.getStartTime(), 
            endTime
        );
        return ResponseEntity.ok().body(isAvailable);
    }

    @GetMapping("/assistant/assigned")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyAssignedAppointments(
        @RequestParam(value = "status", required = false) String status
    ) {
        LOG.debug("REST request to get assigned appointments for current assistant with status: {}", status);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Lấy assistant theo user login của user đang đăng nhập
        Assistant assistant = assistantRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "assistantnotfound"));
        
        // Lấy appointments đã được phân công từ bảng appointment_assistant
        // Query theo assistant_id của user đang đăng nhập
        Long currentAssistantId = assistant.getId();
        LOG.debug("Querying appointments for assistant_id: {}", currentAssistantId);
        List<AppointmentAssistant> appointmentAssistants = appointmentAssistantRepository
            .findByAssistantIdWithRelations(currentAssistantId);

        // Convert sang AppointmentActionDTO và filter theo status nếu có
        List<AppointmentActionDTO> actions = appointmentAssistants.stream()
            .map(aa -> {
                // Tìm appointment_action tương ứng với appointment này và assistant này
                Long appointmentId = aa.getAppointment().getId();
                Long assistantUserId = assistant.getUser() != null ? assistant.getUser().getId() : null;
                
                // Lấy appointment_action với action_type = ASSIGN_ASSISTANT
                return appointmentActionService.findByAppointmentId(appointmentId).stream()
                    .filter(action -> "ASSIGN_ASSISTANT".equals(action.getActionType()) &&
                        (assistantUserId == null || assistantUserId.equals(action.getAssignedToId())))
                    .findFirst()
                    .orElse(null);
            })
            .filter(action -> action != null)
            .filter(action -> status == null || status.trim().isEmpty() || status.equals(action.getStatus()))
            .toList();

        return ResponseEntity.ok().body(actions);
    }

    @GetMapping("/assistant/assigned/pending")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyPendingAssignments() {
        LOG.debug("REST request to get pending assigned appointments for current assistant");
        // Sử dụng lại logic của getMyAssignedAppointments với status = PENDING
        return getMyAssignedAppointments("PENDING");
    }

    @GetMapping("/assistant/{id}/detail")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<AppointmentDTO> getAssignedAppointmentDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get appointment detail for assistant: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Lấy assistant theo user login của user đang đăng nhập
        Assistant assistant = assistantRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "assistantnotfound"));

        // Kiểm tra appointment đã được phân công cho assistant này
        Optional<AppointmentAssistant> appointmentAssistant = appointmentAssistantRepository
            .findByAppointmentIdAndAssistantId(id, assistant.getId());

        if (appointmentAssistant.isEmpty()) {
            throw new BadRequestAlertException(
                "Appointment not assigned to you or not found", 
                ENTITY_NAME, 
                "notassigned"
            );
        }

        // Lấy chi tiết appointment
        Optional<AppointmentDTO> appointmentDTO = appointmentService.findOne(id);
        if (appointmentDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(appointmentDTO.get());
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<AppointmentMessageDTO> sendMessage(
        @PathVariable("id") Long appointmentId,
        @Valid @RequestBody SendMessageVM sendMessageVM
    ) {
        LOG.debug("REST request to send message for appointment: {}", appointmentId);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        AppointmentMessageDTO messageDTO = appointmentMessageService.createMessage(
            appointmentId, 
            sendMessageVM.getMessage(), 
            currentUserLogin
        );
        return ResponseEntity.ok().body(messageDTO);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<AppointmentMessageDTO>> getMessages(@PathVariable("id") Long appointmentId) {
        LOG.debug("REST request to get messages for appointment: {}", appointmentId);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentMessageDTO> messages = appointmentMessageService.findByAppointmentId(appointmentId, currentUserLogin);
        return ResponseEntity.ok().body(messages);
    }

    // Request DTOs
    public static class CheckAvailabilityRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @NotNull
        private Long vetId;
        
        @NotNull
        private java.time.ZonedDateTime startTime;
        
        private java.time.ZonedDateTime endTime;

        public Long getVetId() {
            return vetId;
        }

        public void setVetId(Long vetId) {
            this.vetId = vetId;
        }

        public java.time.ZonedDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(java.time.ZonedDateTime startTime) {
            this.startTime = startTime;
        }

        public java.time.ZonedDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(java.time.ZonedDateTime endTime) {
            this.endTime = endTime;
        }
    }
}

