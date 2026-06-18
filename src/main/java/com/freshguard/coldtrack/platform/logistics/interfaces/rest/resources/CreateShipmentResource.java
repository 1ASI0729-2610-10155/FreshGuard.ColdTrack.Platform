package com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/** Request resource used to register a shipment. */
public record CreateShipmentResource(
        @NotBlank @Size(max = 160) String destination,
        @NotBlank @Size(max = 120) String driver,
        @NotBlank @Size(max = 500) String cargoDescription,
        @NotNull LocalDateTime departureAt,
        @NotNull @Future LocalDateTime estimatedArrivalAt) {
}
