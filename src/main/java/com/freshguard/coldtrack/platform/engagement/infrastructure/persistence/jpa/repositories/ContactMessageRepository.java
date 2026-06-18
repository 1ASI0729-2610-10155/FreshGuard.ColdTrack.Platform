package com.freshguard.coldtrack.platform.engagement.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.engagement.domain.model.entities.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> { }
