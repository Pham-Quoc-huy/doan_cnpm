package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.domain.AppointmentAssistant;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.AppointmentAssistantRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.service.AssistantService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.MailService;
import com.docpet.animalhospital.service.UserService;
import com.docpet.animalhospital.service.dto.AdminUserDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.AssistantWithUserDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import com.docpet.animalhospital.web.rest.errors.EmailAlreadyUsedException;
import com.docpet.animalhospital.web.rest.errors.InvalidPasswordException;
import com.docpet.animalhospital.web.rest.errors.LoginAlreadyUsedException;
import com.docpet.animalhospital.web.rest.vm.AssistantRegistrationVM;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vets")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetAssistantController {

    private static final Logger LOG = LoggerFactory.getLogger(VetAssistantController.class);
    private static final String ENTITY_NAME = "assistant";

    private final UserService userService;
    private final UserRepository userRepository;
    private final AssistantRepository assistantRepository;
    private final AssistantService assistantService;
    private final MailService mailService;
    private final AppointmentAssistantRepository appointmentAssistantRepository;
    private final AppointmentService appointmentService;

    public VetAssistantController(
        UserService userService,
        UserRepository userRepository,
        AssistantRepository assistantRepository,
        AssistantService assistantService,
        MailService mailService,
        AppointmentAssistantRepository appointmentAssistantRepository,
        AppointmentService appointmentService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.assistantRepository = assistantRepository;
        this.assistantService = assistantService;
        this.mailService = mailService;
        this.appointmentAssistantRepository = appointmentAssistantRepository;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/assistants")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdminUserDTO> createAssistant(@Valid @RequestBody AssistantRegistrationVM assistantRegistrationVM) {
        LOG.debug("REST request to create Assistant by Vet: {}", assistantRegistrationVM);
        
        if (isPasswordLengthInvalid(assistantRegistrationVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        
        User user = userService.registerAssistant(assistantRegistrationVM, assistantRegistrationVM.getPassword());
        AdminUserDTO userDTO = new AdminUserDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/assistants")
    public ResponseEntity<List<AssistantWithUserDTO>> getAllAssistants() {
        LOG.debug("REST request to get all Assistants for current Vet");
        
        try {
            // Lấy tất cả assistants từ bảng assistant với eager loading user
            List<Assistant> assistants = assistantRepository.findAllWithUser();
            LOG.debug("Found {} assistants", assistants.size());
            
            List<AssistantWithUserDTO> assistantDTOs = assistants.stream()
                .filter(assistant -> assistant.getUser() != null)
                .map(assistant -> {
                    try {
                        return new AssistantWithUserDTO(assistant.getId(), assistant.getUser());
                    } catch (Exception e) {
                        LOG.error("Error creating AssistantWithUserDTO for assistant id: {}", assistant.getId(), e);
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
            
            LOG.debug("Returning {} assistant DTOs", assistantDTOs.size());
            return ResponseEntity.ok().body(assistantDTOs);
        } catch (Exception e) {
            LOG.error("Error getting all assistants", e);
            throw new BadRequestAlertException("Error getting assistants: " + e.getMessage(), ENTITY_NAME, "error");
        }
    }

    @GetMapping("/assistants/{id}")
    public ResponseEntity<AssistantWithUserDTO> getAssistant(@PathVariable Long id) {
        LOG.debug("REST request to get Assistant : {}", id);
        
        Optional<Assistant> assistant = assistantRepository.findByIdWithUser(id);
        if (assistant.isPresent() && assistant.get().getUser() != null) {
            return ResponseEntity.ok().body(new AssistantWithUserDTO(assistant.get().getId(), assistant.get().getUser()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assistants/{id}")
    public ResponseEntity<AdminUserDTO> updateAssistant(@PathVariable Long id, @Valid @RequestBody AdminUserDTO userDTO) {
        LOG.debug("REST request to update Assistant : {}", id);
        
        Assistant assistant = assistantRepository.findByIdWithUser(id)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "notfound"));
        
        if (assistant.getUser() == null) {
            throw new BadRequestAlertException("Assistant does not have a user", ENTITY_NAME, "nouser");
        }
        
        // Update user của assistant
        User user = assistant.getUser();
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        if (userDTO.getLangKey() != null) {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getImageUrl() != null) {
            user.setImageUrl(userDTO.getImageUrl());
        }
        
        User updatedUser = userRepository.save(user);
        
        return ResponseEntity.ok().body(new AdminUserDTO(updatedUser));
    }

    @DeleteMapping("/assistants/{id}")
    public ResponseEntity<Void> deleteAssistant(@PathVariable Long id) {
        LOG.debug("REST request to delete Assistant : {}", id);
        
        Assistant assistant = assistantRepository.findByIdWithUser(id)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "notfound"));
        
        // Lưu lại thông tin user trước khi xóa assistant
        String userLogin = null;
        if (assistant.getUser() != null) {
            userLogin = assistant.getUser().getLogin();
        }
        
        // Xóa Assistant trước để tránh lỗi TransientObjectException
        assistantRepository.delete(assistant);
        assistantRepository.flush(); // Đảm bảo xóa assistant trước
        
        // Sau đó mới xóa User (nếu có)
        if (userLogin != null) {
            userService.deleteUser(userLogin);
        }
        
        return ResponseEntity.noContent().build();
    }

    /**
     * Lấy danh sách appointments đã được phân công cho một assistant cụ thể
     * GET /api/vets/assistants/{assistantId}/appointments
     * 
     * Xử lý các trường hợp:
     * - assistantId không tồn tại → 404 Not Found
     * - assistantId hợp lệ nhưng không có appointments → 200 OK với mảng rỗng []
     * - assistantId null hoặc không hợp lệ → 400 Bad Request
     * 
     * @param assistantId ID của assistant (assistant.id, không phải userId)
     * @return Danh sách AppointmentDTO (có thể rỗng nếu assistant chưa có appointments)
     */
    @GetMapping("/assistants/{assistantId}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAssistantAppointments(@PathVariable Long assistantId) {
        LOG.debug("REST request to get appointments for assistant id: {}", assistantId);
        
        // Validate assistantId
        if (assistantId == null) {
            LOG.warn("Invalid request: assistantId is null");
            throw new BadRequestAlertException("Assistant ID không được để trống", ENTITY_NAME, "invalidid");
        }
        
        if (assistantId <= 0) {
            LOG.warn("Invalid request: assistantId is invalid: {}", assistantId);
            throw new BadRequestAlertException("Assistant ID không hợp lệ", ENTITY_NAME, "invalidid");
        }
        
        // Kiểm tra assistant có tồn tại không
        Optional<Assistant> assistantOpt = assistantRepository.findByIdWithUser(assistantId);
        if (assistantOpt.isEmpty()) {
            LOG.warn("Assistant not found with id: {}", assistantId);
            throw new BadRequestAlertException(
                "Assistant không tồn tại với ID: " + assistantId, 
                ENTITY_NAME, 
                "notfound"
            );
        }
        
        Assistant assistant = assistantOpt.get();
        LOG.debug("Found assistant: id={}, userId={}", assistant.getId(), 
            assistant.getUser() != null ? assistant.getUser().getId() : "null");
        
        // Lấy appointments đã được phân công từ bảng appointment_assistant
        List<AppointmentAssistant> appointmentAssistants = appointmentAssistantRepository
            .findByAssistantIdWithRelations(assistantId);
        
        LOG.debug("Found {} appointment_assistant records for assistant id: {}", 
            appointmentAssistants.size(), assistantId);
        
        // Convert sang AppointmentDTO
        List<AppointmentDTO> appointments = appointmentAssistants.stream()
            .map(aa -> {
                try {
                    Long appointmentId = aa.getAppointment().getId();
                    Optional<AppointmentDTO> appointmentOpt = appointmentService.findOne(appointmentId);
                    if (appointmentOpt.isEmpty()) {
                        LOG.warn("Appointment not found with id: {} (referenced by appointment_assistant)", appointmentId);
                        return null;
                    }
                    return appointmentOpt.get();
                } catch (Exception e) {
                    LOG.error("Error converting AppointmentAssistant to AppointmentDTO", e);
                    return null;
                }
            })
            .filter(appointment -> appointment != null)
            .collect(Collectors.toList());
        
        LOG.debug("Returning {} appointment DTOs for assistant id: {}", appointments.size(), assistantId);
        
        // Trả về danh sách (có thể rỗng nếu assistant chưa có appointments)
        return ResponseEntity.ok().body(appointments);
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < AssistantRegistrationVM.PASSWORD_MIN_LENGTH ||
            password.length() > AssistantRegistrationVM.PASSWORD_MAX_LENGTH
        );
    }
}

