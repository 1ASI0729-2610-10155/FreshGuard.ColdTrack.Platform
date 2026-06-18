package com.freshguard.coldtrack.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.profiles.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
}
