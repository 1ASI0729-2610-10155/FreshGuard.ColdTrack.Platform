package com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.monitoring.domain.model.entities.TelemetryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelemetryLogRepository extends JpaRepository<TelemetryLog, Long> {
    List<TelemetryLog> findAllByShipmentCodeOrderByRecordedAtAsc(String shipmentCode);
    List<TelemetryLog> findAllBySensorCodeOrderByRecordedAtAsc(String sensorCode);
}
