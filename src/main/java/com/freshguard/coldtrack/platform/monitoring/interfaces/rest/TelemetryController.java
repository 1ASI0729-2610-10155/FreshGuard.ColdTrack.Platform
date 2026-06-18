package com.freshguard.coldtrack.platform.monitoring.interfaces.rest;

import com.freshguard.coldtrack.platform.monitoring.application.internal.commandservices.TelemetryCommandService;
import com.freshguard.coldtrack.platform.monitoring.domain.model.entities.TelemetryLog;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.TelemetryLogRepository;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.CreateTelemetryReadingResource;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.TelemetryReadingResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TelemetryController {
    private final TelemetryCommandService commandService;
    private final TelemetryLogRepository repository;

    public TelemetryController(TelemetryCommandService commandService, TelemetryLogRepository repository) {
        this.commandService = commandService;
        this.repository = repository;
    }

    @PostMapping("/telemetry-readings")
    public ResponseEntity<TelemetryReadingResource> record(@Valid @RequestBody CreateTelemetryReadingResource resource) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toResource(commandService.record(resource)));
    }

    @GetMapping("/shipments/{shipmentCode}/telemetry-readings")
    public List<TelemetryReadingResource> byShipment(@PathVariable String shipmentCode) {
        return repository.findAllByShipmentCodeOrderByRecordedAtAsc(shipmentCode).stream().map(this::toResource).toList();
    }

    private TelemetryReadingResource toResource(TelemetryLog log) {
        return new TelemetryReadingResource(log.getId(), log.getSensorCode(), log.getShipmentCode(), log.getTemperature(), log.getHumidity(), log.getRecordedAt());
    }
}
