package com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

/**
 * Base persistence entity with identity and audit timestamps.
 *
 * @author HackRats
 */
@Getter
@MappedSuperclass
public abstract class AuditableAbstractPersistenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
