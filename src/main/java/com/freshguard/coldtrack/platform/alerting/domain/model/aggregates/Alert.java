package com.freshguard.coldtrack.platform.alerting.domain.model.aggregates;

import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.*;
import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/** Aggregate root for an environmental or connectivity incident. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "alerts")
public class Alert extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true, length = 20)
    private String alertCode;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AlertSeverity severity;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AlertStatus status;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AlertType type;
    @Column(nullable = false, length = 20)
    private String shipmentCode;
    @Column(nullable = false, length = 40)
    private String sensorCode;
    @Column(nullable = false, length = 500)
    private String message;
    private Double measuredValue;
    private Double thresholdValue;
    @Column(nullable = false)
    private Instant triggeredAt;
    private Instant resolvedAt;

    public Alert(AlertSeverity severity, AlertType type, String shipmentCode, String sensorCode,
                 String message, Double measuredValue, Double thresholdValue) {
        this.alertCode = "ALT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.severity = severity;
        this.status = AlertStatus.ACTIVE;
        this.type = type;
        this.shipmentCode = shipmentCode;
        this.sensorCode = sensorCode;
        this.message = message;
        this.measuredValue = measuredValue;
        this.thresholdValue = thresholdValue;
        this.triggeredAt = Instant.now();
    }

    public void acknowledge() {
        if (status == AlertStatus.ACTIVE) status = AlertStatus.ACKNOWLEDGED;
    }

    public void resolve() {
        status = AlertStatus.RESOLVED;
        resolvedAt = Instant.now();
    }
}
