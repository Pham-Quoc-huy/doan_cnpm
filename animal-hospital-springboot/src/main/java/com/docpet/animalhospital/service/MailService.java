package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        // In a real application, you would send an email here
        // For now, just log it
    }

    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        // In a real application, you would send an email here
        // For now, just log it
    }

    public void sendWelcomeEmail(User user) {
        log.debug("Sending welcome email to '{}'", user.getEmail());
        // In a real application, you would send an email here
        // For now, just log it
    }
}

