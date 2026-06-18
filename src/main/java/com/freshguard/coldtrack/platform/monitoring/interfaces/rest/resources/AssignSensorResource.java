package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/** Sensor assignment request embedded under a shipment resource. */
public record AssignSensorResource(@NotBlank String sensorCode) {
}
