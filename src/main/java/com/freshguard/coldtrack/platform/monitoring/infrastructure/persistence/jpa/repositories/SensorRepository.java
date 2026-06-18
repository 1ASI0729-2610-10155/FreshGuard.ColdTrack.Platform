package com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.monitoring.domain.model.aggregates.Sensor;
import com.freshguard.coldtrack.platform.monitoring.domain.model.valueobjects.SensorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findBySensorCode(String sensorCode);
    boolean existsBySensorCode(String sensorCode);
    List<Sensor> findAllByStatus(SensorStatus status);
    long countByStatus(SensorStatus status);
}
