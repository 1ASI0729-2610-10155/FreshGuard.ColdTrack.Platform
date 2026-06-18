package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources;

import java.time.Instant;

/** Persisted telemetry reading returned by the API. */
public record TelemetryReadingResource(Long id, String sensorCode, String shipmentCode,
                                       double temperature, double humidity, Instant recordedAt) {
}
