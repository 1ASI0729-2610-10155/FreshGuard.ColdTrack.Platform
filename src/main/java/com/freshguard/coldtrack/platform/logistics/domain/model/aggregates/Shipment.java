package com.freshguard.coldtrack.platform.logistics.domain.model.aggregates;

import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/** Aggregate root governing the lifecycle of a cold-chain shipment. */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "shipments")
public class Shipment extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true, length = 20)
    private String shipmentCode;
    @Column(nullable = false, length = 160)
    private String destination;
    @Column(nullable = false, length = 120)
    private String driverName;
    @Column(nullable = false, length = 500)
    private String cargoDescription;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private ShipmentStatus status;
    private Double currentTemperature;
    private Double currentHumidity;
    @Column(nullable = false)
    private LocalDateTime departureAt;
    @Column(nullable = false)
    private LocalDateTime estimatedArrivalAt;
    private LocalDateTime completedAt;

    public Shipment(String destination, String driverName, String cargoDescription,
                    LocalDateTime departureAt, LocalDateTime estimatedArrivalAt) {
        if (!estimatedArrivalAt.isAfter(departureAt)) {
            throw new ConflictException("Estimated arrival must be after departure");
        }
        this.shipmentCode = "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.destination = destination.trim();
        this.driverName = driverName.trim();
        this.cargoDescription = cargoDescription.trim();
        this.departureAt = departureAt;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.status = ShipmentStatus.REGISTERED;
    }

    public void start() {
        if (status != ShipmentStatus.REGISTERED) throw new ConflictException("Only registered shipments can start");
        status = ShipmentStatus.IN_TRANSIT;
    }

    public void complete() {
        if (status != ShipmentStatus.IN_TRANSIT) throw new ConflictException("Only in-transit shipments can complete");
        status = ShipmentStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == ShipmentStatus.COMPLETED) throw new ConflictException("Completed shipments cannot be cancelled");
        status = ShipmentStatus.CANCELLED;
    }

    public void updateConditions(double temperature, double humidity) {
        currentTemperature = temperature;
        currentHumidity = humidity;
    }
}
