package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.AssistantDTO;
import com.docpet.animalhospital.service.mapper.AssistantMapper;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AssistantService {

    private static final Logger LOG = LoggerFactory.getLogger(AssistantService.class);
    private static final String ENTITY_NAME = "assistant";

    private final AssistantRepository assistantRepository;
    private final AssistantMapper assistantMapper;
    private final UserRepository userRepository;

    public AssistantService(AssistantRepository assistantRepository, AssistantMapper assistantMapper, UserRepository userRepository) {
        this.assistantRepository = assistantRepository;
        this.assistantMapper = assistantMapper;
        this.userRepository = userRepository;
    }

    public AssistantDTO save(AssistantDTO assistantDTO) {
        LOG.debug("Request to save Assistant : {}", assistantDTO);
        Assistant assistant = assistantMapper.toEntity(assistantDTO);
        // Ensure user is set if userId is provided
        if (assistantDTO.getUserId() != null) {
            userRepository.findById(assistantDTO.getUserId()).ifPresent(assistant::setUser);
        }
        assistant = assistantRepository.save(assistant);
        return assistantMapper.toDto(assistant);
    }

    public AssistantDTO update(AssistantDTO assistantDTO) {
        LOG.debug("Request to update Assistant : {}", assistantDTO);
        Assistant existingAssistant = assistantRepository.findById(assistantDTO.getId())
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "notfound"));

        // Update user if userId is provided
        if (assistantDTO.getUserId() != null) {
            userRepository.findById(assistantDTO.getUserId()).ifPresent(existingAssistant::setUser);
        }
        // No other fields in Assistant entity currently, but can be added here
        existingAssistant = assistantRepository.save(existingAssistant);
        return assistantMapper.toDto(existingAssistant);
    }

    @Transactional(readOnly = true)
    public Page<AssistantDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Assistants");
        return assistantRepository.findAll(pageable).map(assistantMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AssistantDTO> findOne(Long id) {
        LOG.debug("Request to get Assistant : {}", id);
        return assistantRepository.findById(id).map(assistantMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Assistant : {}", id);
        assistantRepository.deleteById(id);
    }
}



