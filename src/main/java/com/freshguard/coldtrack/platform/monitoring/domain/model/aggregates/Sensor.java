package com.freshguard.coldtrack.platform.monitoring.domain.model.aggregates;

import com.freshguard.coldtrack.platform.monitoring.domain.model.valueobjects.SensorStatus;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** Aggregate root representing a physical IoT sensor. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "sensors")
public class Sensor extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true, length = 40)
    private String sensorCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SensorStatus status;
    @Column(length = 20)
    private String assignedShipmentCode;
    private Instant lastReadingAt;
    private Double temperature;
    private Double humidity;

    public Sensor(String sensorCode) {
        this.sensorCode = sensorCode.trim().toUpperCase();
        this.status = SensorStatus.AVAILABLE;
    }

    public void assignTo(String shipmentCode) {
        if (status == SensorStatus.ASSIGNED) throw new ConflictException("Sensor is already assigned");
        assignedShipmentCode = shipmentCode;
        status = SensorStatus.ASSIGNED;
    }

    public void unassign() {
        assignedShipmentCode = null;
        status = SensorStatus.AVAILABLE;
    }

    public void record(double temperature, double humidity, Instant recordedAt) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.lastReadingAt = recordedAt;
    }
}
