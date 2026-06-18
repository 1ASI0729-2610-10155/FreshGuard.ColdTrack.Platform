package com.freshguard.coldtrack.platform.engagement.domain.model.entities;

import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contact_messages")
public class ContactMessage extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 120) private String fullName;
    @Column(nullable = false, length = 160) private String email;
    @Column(nullable = false, length = 1000) private String message;

    public ContactMessage(String fullName, String email, String message) {
        this.fullName = fullName;
        this.email = email;
        this.message = message;
    }
}
