package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Message;
import com.portfolio.backend.repository.MessageRepository;
import com.portfolio.backend.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final MessageRepository messageRepository;
    private final EmailService emailService;

    public ContactController(
            MessageRepository messageRepository,
            EmailService emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    // Public contact form
    @PostMapping
    public ResponseEntity<Message> submitContactForm(
            @RequestBody Message message) {

        Message savedMessage = messageRepository.save(message);

        emailService.sendContactEmail(
                message.getName(),
                message.getEmail(),
                message.getSubject(),
                message.getMessage()
        );

        return ResponseEntity.ok(savedMessage);
    }

    // Admin: view all messages
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(
                messageRepository.findAll()
        );
    }

    // Admin: mark message as read/unread
    @PutMapping("/{id}/read")
    public ResponseEntity<Message> updateReadStatus(
            @PathVariable Long id,
            @RequestParam boolean read) {

        Message message = messageRepository
                .findById(id)
                .orElse(null);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        message.setRead(read);

        return ResponseEntity.ok(
                messageRepository.save(message)
        );
    }

    // Admin: delete message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long id) {

        if (!messageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        messageRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}