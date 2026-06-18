package com.freshguard.coldtrack.platform.monitoring.interfaces.rest;

import com.freshguard.coldtrack.platform.monitoring.application.internal.commandservices.SensorCommandService;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.SensorRepository;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.CreateSensorResource;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.SensorResource;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.transform.SensorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
@Tag(name = "Sensors")
public class SensorsController {
    private final SensorCommandService commandService;
    private final SensorRepository repository;

    public SensorsController(SensorCommandService commandService, SensorRepository repository) {
        this.commandService = commandService;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<SensorResource> create(@Valid @RequestBody CreateSensorResource resource) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SensorResourceFromEntityAssembler.toResource(commandService.create(resource)));
    }

    @GetMapping
    public List<SensorResource> findAll() {
        return repository.findAll().stream().map(SensorResourceFromEntityAssembler::toResource).toList();
    }

    @DeleteMapping("/{sensorCode}/assignment")
    public SensorResource unassign(@PathVariable String sensorCode) {
        return SensorResourceFromEntityAssembler.toResource(commandService.unassign(sensorCode));
    }
}
