package com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects;

/** Lifecycle states supported by a shipment. */
public enum ShipmentStatus {
    REGISTERED,
    IN_TRANSIT,
    COMPLETED,
    CANCELLED
}
