package com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories;

import com.freshguard.coldtrack.platform.logistics.domain.model.aggregates.Shipment;
import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByShipmentCode(String shipmentCode);
    List<Shipment> findAllByStatus(ShipmentStatus status);
    long countByStatus(ShipmentStatus status);
}
