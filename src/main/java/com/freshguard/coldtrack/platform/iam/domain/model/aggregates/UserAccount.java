package com.freshguard.coldtrack.platform.iam.domain.model.aggregates;

import com.freshguard.coldtrack.platform.iam.domain.model.entities.Role;
import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/** Aggregate root that protects user credentials and role membership. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class UserAccount extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserAccount(String fullName, String email, String passwordHash, Set<Role> roles) {
        this.fullName = fullName.trim();
        this.email = email.trim().toLowerCase();
        this.passwordHash = passwordHash;
        this.roles = new HashSet<>(roles);
    }
}
