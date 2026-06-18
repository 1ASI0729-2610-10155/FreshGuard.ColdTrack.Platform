package com.freshguard.coldtrack.platform.profiles.domain.model.aggregates;

import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Profile data separated from authentication credentials. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "profiles")
public class Profile extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, unique = true, length = 160)
    private String email;
    @Column(length = 30)
    private String phoneNumber;

    public Profile(Long userId, String fullName, String email, String phoneNumber) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
