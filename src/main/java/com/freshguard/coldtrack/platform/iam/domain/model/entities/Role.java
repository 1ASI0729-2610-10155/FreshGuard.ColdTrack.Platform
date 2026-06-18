package com.freshguard.coldtrack.platform.iam.domain.model.entities;

import com.freshguard.coldtrack.platform.iam.domain.model.valueobjects.RoleName;
import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Authorization role assigned to platform users. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "roles")
public class Role extends AuditableAbstractPersistenceEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 40)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }
}
