package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.service.MailService;
import com.docpet.animalhospital.service.UserService;
import com.docpet.animalhospital.service.dto.AdminUserDTO;
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
    private final MailService mailService;

    public VetAssistantController(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
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
    public ResponseEntity<List<AdminUserDTO>> getAllAssistants() {
        LOG.debug("REST request to get all Assistants for current Vet");
        
        List<User> assistants = userRepository.findAllByAuthorities_Name(AuthoritiesConstants.ASSISTANT);
        List<AdminUserDTO> assistantDTOs = assistants.stream()
            .map(AdminUserDTO::new)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok().body(assistantDTOs);
    }

    @GetMapping("/assistants/{login}")
    public ResponseEntity<AdminUserDTO> getAssistant(@PathVariable String login) {
        LOG.debug("REST request to get Assistant : {}", login);
        
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent() && user.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            return ResponseEntity.ok().body(new AdminUserDTO(user.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assistants/{login}")
    public ResponseEntity<AdminUserDTO> updateAssistant(@PathVariable String login, @Valid @RequestBody AdminUserDTO userDTO) {
        LOG.debug("REST request to update Assistant : {}", login);
        
        Optional<User> existingUser = userRepository.findOneByLogin(login);
        if (existingUser.isPresent() && existingUser.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            
            userService.updateUser(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getLangKey(),
                userDTO.getImageUrl()
            );
            
            return ResponseEntity.ok().body(new AdminUserDTO(existingUser.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/assistants/{login}")
    public ResponseEntity<Void> deleteAssistant(@PathVariable String login) {
        LOG.debug("REST request to delete Assistant : {}", login);
        
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent() && user.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            
            userService.deleteUser(login);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < AssistantRegistrationVM.PASSWORD_MIN_LENGTH ||
            password.length() > AssistantRegistrationVM.PASSWORD_MAX_LENGTH
        );
    }
}

