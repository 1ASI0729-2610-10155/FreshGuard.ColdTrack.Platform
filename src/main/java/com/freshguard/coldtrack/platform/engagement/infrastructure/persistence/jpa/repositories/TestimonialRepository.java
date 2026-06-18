package com.freshguard.coldtrack.platform.engagement.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.engagement.domain.model.entities.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findAllByPublishedTrue();
}
