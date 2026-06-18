package com.freshguard.coldtrack.platform.monitoring.interfaces.rest;

import com.freshguard.coldtrack.platform.monitoring.application.internal.commandservices.SensorCommandService;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.AssignSensorResource;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.SensorResource;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.transform.SensorResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments/{shipmentCode}/sensor-assignments")
public class ShipmentSensorAssignmentsController {
    private final SensorCommandService service;

    public ShipmentSensorAssignmentsController(SensorCommandService service) {
        this.service = service;
    }

    @PostMapping
    public SensorResource assign(@PathVariable String shipmentCode, @Valid @RequestBody AssignSensorResource resource) {
        return SensorResourceFromEntityAssembler.toResource(service.assign(shipmentCode, resource.sensorCode()));
    }
}
