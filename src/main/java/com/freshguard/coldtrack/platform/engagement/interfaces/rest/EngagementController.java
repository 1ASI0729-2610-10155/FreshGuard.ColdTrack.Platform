package com.freshguard.coldtrack.platform.engagement.interfaces.rest;

import com.freshguard.coldtrack.platform.engagement.domain.model.entities.ContactMessage;
import com.freshguard.coldtrack.platform.engagement.infrastructure.persistence.jpa.repositories.ContactMessageRepository;
import com.freshguard.coldtrack.platform.engagement.infrastructure.persistence.jpa.repositories.TestimonialRepository;
import com.freshguard.coldtrack.platform.engagement.interfaces.rest.resources.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EngagementController {
    private final ContactMessageRepository messages;
    private final TestimonialRepository testimonials;

    public EngagementController(ContactMessageRepository messages, TestimonialRepository testimonials) {
        this.messages = messages;
        this.testimonials = testimonials;
    }

    @PostMapping("/contact-messages")
    public ResponseEntity<ContactMessageResource> createMessage(@Valid @RequestBody CreateContactMessageResource resource) {
        var saved = messages.save(new ContactMessage(resource.fullName(), resource.email(), resource.message()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContactMessageResource(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getMessage()));
    }

    @GetMapping("/testimonials")
    public List<TestimonialResource> testimonials() {
        return testimonials.findAllByPublishedTrue().stream()
                .map(item -> new TestimonialResource(item.getId(), item.getAuthorName(), item.getAuthorRole(), item.getContent())).toList();
    }
}
