package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.LabTest;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.LabTestRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.LabTestDTO;
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
public class LabTestService {

    private static final Logger LOG = LoggerFactory.getLogger(LabTestService.class);

    private final LabTestRepository labTestRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public LabTestService(
        LabTestRepository labTestRepository,
        AppointmentRepository appointmentRepository,
        PetRepository petRepository,
        UserRepository userRepository
    ) {
        this.labTestRepository = labTestRepository;
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public LabTestDTO createLabTest(
        Long appointmentId,
        String testName,
        String testType,
        String description,
        String requestedByLogin,
        String assignedToLogin
    ) {
        LOG.debug("Request to create LabTest for appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "labTest", "notfound"));

        User requestedBy = userRepository.findOneByLogin(requestedByLogin)
            .orElseThrow(() -> new BadRequestAlertException("User not found", "labTest", "usernotfound"));

        LabTest labTest = new LabTest();
        labTest.setAppointment(appointment);
        labTest.setPet(appointment.getPet());
        labTest.setTestName(testName);
        labTest.setTestType(testType);
        labTest.setDescription(description);
        labTest.setStatus("REQUESTED");
        labTest.setRequestedBy(requestedBy);
        labTest.setRequestedDate(ZonedDateTime.now());

        if (assignedToLogin != null && !assignedToLogin.trim().isEmpty()) {
            User assignedTo = userRepository.findOneByLogin(assignedToLogin)
                .orElseThrow(() -> new BadRequestAlertException("Assigned user not found", "labTest", "assignednotfound"));
            labTest.setAssignedTo(assignedTo);
        }

        labTest = labTestRepository.save(labTest);
        return toDto(labTest);
    }

    @Transactional(readOnly = true)
    public List<LabTestDTO> findByAppointmentId(Long appointmentId) {
        LOG.debug("Request to get LabTests for appointment: {}", appointmentId);
        return labTestRepository.findByAppointment_Id(appointmentId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private LabTestDTO toDto(LabTest labTest) {
        LabTestDTO dto = new LabTestDTO();
        dto.setId(labTest.getId());
        dto.setTestName(labTest.getTestName());
        dto.setTestType(labTest.getTestType());
        dto.setDescription(labTest.getDescription());
        dto.setStatus(labTest.getStatus());
        dto.setResult(labTest.getResult());
        dto.setNotes(labTest.getNotes());
        dto.setRequestedDate(labTest.getRequestedDate());
        dto.setCompletedDate(labTest.getCompletedDate());
        dto.setSampleCollectedDate(labTest.getSampleCollectedDate());
        if (labTest.getAppointment() != null) {
            dto.setAppointmentId(labTest.getAppointment().getId());
        }
        if (labTest.getPet() != null) {
            dto.setPetId(labTest.getPet().getId());
        }
        if (labTest.getRequestedBy() != null) {
            dto.setRequestedById(labTest.getRequestedBy().getId());
        }
        if (labTest.getAssignedTo() != null) {
            dto.setAssignedToId(labTest.getAssignedTo().getId());
        }
        return dto;
    }
}

