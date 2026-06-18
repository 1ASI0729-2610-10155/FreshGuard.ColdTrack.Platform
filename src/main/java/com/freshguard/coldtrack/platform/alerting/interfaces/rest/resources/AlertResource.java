package com.freshguard.coldtrack.platform.alerting.interfaces.rest.resources;

import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.*;

import java.time.Instant;

/** REST representation of an operational alert. */
public record AlertResource(String id, AlertSeverity severity, AlertStatus status, AlertType type,
                            String shipmentId, String sensorId, String message, Instant createdAt,
                            Double value, Double threshold) {
}
