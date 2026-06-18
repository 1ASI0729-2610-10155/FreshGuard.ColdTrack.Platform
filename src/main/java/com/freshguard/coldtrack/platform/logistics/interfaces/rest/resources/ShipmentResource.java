package com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources;

import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;

import java.time.LocalDateTime;

/** REST representation of a shipment. */
public record ShipmentResource(
        String id,
        String destination,
        ShipmentStatus status,
        String driver,
        String cargoDescription,
        Double temperature,
        Double humidity,
        LocalDateTime departureAt,
        LocalDateTime estimatedArrivalAt) {
}
