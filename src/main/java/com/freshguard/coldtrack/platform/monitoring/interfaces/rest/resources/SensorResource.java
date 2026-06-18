package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources;

import com.freshguard.coldtrack.platform.monitoring.domain.model.valueobjects.SensorStatus;

import java.time.Instant;

/** REST representation of a sensor compatible with ColdTrack Front. */
public record SensorResource(String id, SensorStatus status, String assignedShipmentId,
                             Instant lastReadingAt, Double temperature, Double humidity) {
}
