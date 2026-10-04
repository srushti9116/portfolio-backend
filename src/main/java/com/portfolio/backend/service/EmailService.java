package com.portfolio.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${portfolio.contact.email}")
    private String contactEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(
            String name,
            String email,
            String subject,
            String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(contactEmail);
        mailMessage.setReplyTo(email);

        mailMessage.setSubject(
                "Portfolio Contact: " + subject
        );

        mailMessage.setText(
                "You received a new message from your portfolio website.\n\n"
                + "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Subject: " + subject + "\n\n"
                + "Message:\n"
                + message
        );

        mailSender.send(mailMessage);
    }
}