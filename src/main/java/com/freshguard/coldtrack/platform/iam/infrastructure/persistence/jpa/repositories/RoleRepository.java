package com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.iam.domain.model.entities.Role;
import com.freshguard.coldtrack.platform.iam.domain.model.valueobjects.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
