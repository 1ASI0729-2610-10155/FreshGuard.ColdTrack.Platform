package com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.alerting.domain.model.aggregates.Alert;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertSeverity;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByAlertCode(String code);
    List<Alert> findAllByStatus(AlertStatus status);
    List<Alert> findAllBySeverity(AlertSeverity severity);
    List<Alert> findAllByShipmentCode(String shipmentCode);
    long countByStatus(AlertStatus status);
    long countBySeverity(AlertSeverity severity);
}
