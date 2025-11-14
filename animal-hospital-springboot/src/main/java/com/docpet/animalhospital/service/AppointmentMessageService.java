package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.AppointmentMessage;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.AppointmentMessageRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.service.dto.AppointmentMessageDTO;
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
public class AppointmentMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentMessageService.class);

    private final AppointmentMessageRepository appointmentMessageRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    public AppointmentMessageService(
        AppointmentMessageRepository appointmentMessageRepository,
        AppointmentRepository appointmentRepository,
        UserRepository userRepository,
        OwnerRepository ownerRepository,
        VetRepository vetRepository
    ) {
        this.appointmentMessageRepository = appointmentMessageRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
    }

    public AppointmentMessageDTO createMessage(Long appointmentId, String message, String currentUserLogin) {
        LOG.debug("Request to create message for appointment: {} by user: {}", appointmentId, currentUserLogin);

        // Validate message không rỗng
        if (message == null || message.trim().isEmpty()) {
            throw new BadRequestAlertException("Message cannot be empty", "appointmentMessage", "messageempty");
        }

        // Validate message không quá 1000 ký tự
        if (message.length() > 1000) {
            throw new BadRequestAlertException("Message cannot exceed 1000 characters", "appointmentMessage", "messagetoolong");
        }

        // Lấy appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointmentMessage", "appointmentnotfound"));

        // Validate appointment phải có vet_id (đã chọn bác sĩ)
        if (appointment.getVet() == null) {
            throw new BadRequestAlertException("Appointment must have a vet assigned", "appointmentMessage", "novetassigned");
        }

        // Lấy current user
        User currentUser = userRepository.findOneByLogin(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("User not found", "appointmentMessage", "usernotfound"));

        // Validate: current user phải là owner hoặc vet của appointment
        boolean isOwner = false;
        boolean isVet = false;

        // Kiểm tra xem có phải owner không
        if (appointment.getOwner() != null && appointment.getOwner().getUser() != null) {
            isOwner = appointment.getOwner().getUser().getLogin().equals(currentUserLogin);
        }

        // Kiểm tra xem có phải vet không
        if (appointment.getVet() != null && appointment.getVet().getUser() != null) {
            isVet = appointment.getVet().getUser().getLogin().equals(currentUserLogin);
        }

        if (!isOwner && !isVet) {
            throw new BadRequestAlertException(
                "You can only send messages for appointments where you are the owner or the assigned vet",
                "appointmentMessage",
                "notauthorized"
            );
        }

        // Tạo message
        AppointmentMessage appointmentMessage = new AppointmentMessage();
        appointmentMessage.setMessage(message.trim());
        appointmentMessage.setTimestamp(ZonedDateTime.now());
        appointmentMessage.setAppointment(appointment);
        appointmentMessage.setSender(currentUser);

        appointmentMessage = appointmentMessageRepository.save(appointmentMessage);
        return toDto(appointmentMessage);
    }

    @Transactional(readOnly = true)
    public List<AppointmentMessageDTO> findByAppointmentId(Long appointmentId, String currentUserLogin) {
        LOG.debug("Request to get messages for appointment: {} by user: {}", appointmentId, currentUserLogin);

        // Lấy appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointmentMessage", "appointmentnotfound"));

        // Validate appointment phải có vet_id (đã chọn bác sĩ)
        if (appointment.getVet() == null) {
            throw new BadRequestAlertException("Appointment must have a vet assigned", "appointmentMessage", "novetassigned");
        }

        // Validate: current user phải là owner hoặc vet của appointment
        boolean isOwner = false;
        boolean isVet = false;

        // Kiểm tra xem có phải owner không
        if (appointment.getOwner() != null && appointment.getOwner().getUser() != null) {
            isOwner = appointment.getOwner().getUser().getLogin().equals(currentUserLogin);
        }

        // Kiểm tra xem có phải vet không
        if (appointment.getVet() != null && appointment.getVet().getUser() != null) {
            isVet = appointment.getVet().getUser().getLogin().equals(currentUserLogin);
        }

        if (!isOwner && !isVet) {
            throw new BadRequestAlertException(
                "You can only view messages for appointments where you are the owner or the assigned vet",
                "appointmentMessage",
                "notauthorized"
            );
        }

        // Lấy messages và sắp xếp theo timestamp tăng dần (cũ nhất trước)
        List<AppointmentMessage> messages = appointmentMessageRepository.findByAppointmentIdOrderByTimestampAsc(appointmentId);
        return messages.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    private AppointmentMessageDTO toDto(AppointmentMessage message) {
        AppointmentMessageDTO dto = new AppointmentMessageDTO();
        dto.setId(message.getId());
        dto.setMessage(message.getMessage());
        dto.setTimestamp(message.getTimestamp());
        
        if (message.getAppointment() != null) {
            dto.setAppointmentId(message.getAppointment().getId());
        }
        
        if (message.getSender() != null) {
            dto.setSenderId(message.getSender().getId());
            dto.setSenderLogin(message.getSender().getLogin());
            
            // Lấy tên người gửi
            String senderName = message.getSender().getFirstName();
            if (senderName == null || senderName.trim().isEmpty()) {
                senderName = message.getSender().getLogin();
            } else {
                if (message.getSender().getLastName() != null && !message.getSender().getLastName().trim().isEmpty()) {
                    senderName += " " + message.getSender().getLastName();
                }
            }
            dto.setSenderName(senderName);
        }
        
        return dto;
    }
}

