package com.freshguard.coldtrack.platform.monitoring.domain.model.entities;

import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** Immutable environmental reading received from a sensor. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "telemetry_logs")
public class TelemetryLog extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, length = 40)
    private String sensorCode;
    @Column(nullable = false, length = 20)
    private String shipmentCode;
    @Column(nullable = false)
    private double temperature;
    @Column(nullable = false)
    private double humidity;
    @Column(nullable = false)
    private Instant recordedAt;

    public TelemetryLog(String sensorCode, String shipmentCode, double temperature, double humidity, Instant recordedAt) {
        this.sensorCode = sensorCode;
        this.shipmentCode = shipmentCode;
        this.temperature = temperature;
        this.humidity = humidity;
        this.recordedAt = recordedAt;
    }
}
