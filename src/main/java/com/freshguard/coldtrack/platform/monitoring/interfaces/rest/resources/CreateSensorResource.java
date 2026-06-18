package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Request used to register a physical sensor. */
public record CreateSensorResource(@NotBlank @Pattern(regexp = "SENS-[A-Za-z0-9-]+") String id) {
}
