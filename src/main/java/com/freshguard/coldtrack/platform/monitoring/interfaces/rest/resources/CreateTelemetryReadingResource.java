package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources;

import jakarta.validation.constraints.*;

import java.time.Instant;

/** Environmental reading posted by an IoT device or simulator. */
public record CreateTelemetryReadingResource(
        @NotBlank String sensorCode,
        @DecimalMin("-50.0") @DecimalMax("100.0") double temperature,
        @DecimalMin("0.0") @DecimalMax("100.0") double humidity,
        @NotNull Instant recordedAt) {
}
