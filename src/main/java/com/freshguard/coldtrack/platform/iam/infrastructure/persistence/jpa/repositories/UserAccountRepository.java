package com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.iam.domain.model.aggregates.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
