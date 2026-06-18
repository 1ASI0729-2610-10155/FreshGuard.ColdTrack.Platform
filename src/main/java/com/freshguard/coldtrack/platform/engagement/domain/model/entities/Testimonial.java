package com.freshguard.coldtrack.platform.engagement.domain.model.entities;

import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "testimonials")
public class Testimonial extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 120) private String authorName;
    @Column(nullable = false, length = 120) private String authorRole;
    @Column(nullable = false, length = 800) private String content;
    @Column(nullable = false) private boolean published;

    public Testimonial(String authorName, String authorRole, String content, boolean published) {
        this.authorName = authorName;
        this.authorRole = authorRole;
        this.content = content;
        this.published = published;
    }
}
