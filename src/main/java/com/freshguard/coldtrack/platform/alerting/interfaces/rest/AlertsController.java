package com.freshguard.coldtrack.platform.alerting.interfaces.rest;

import com.freshguard.coldtrack.platform.alerting.application.internal.commandservices.AlertCommandService;
import com.freshguard.coldtrack.platform.alerting.domain.model.aggregates.Alert;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertSeverity;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertStatus;
import com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.freshguard.coldtrack.platform.alerting.interfaces.rest.resources.AlertResource;
import com.freshguard.coldtrack.platform.alerting.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts")
public class AlertsController {
    private final AlertRepository repository;
    private final AlertCommandService commandService;

    public AlertsController(AlertRepository repository, AlertCommandService commandService) {
        this.repository = repository;
        this.commandService = commandService;
    }

    @GetMapping
    public List<AlertResource> findAll(@RequestParam(required = false) AlertStatus status,
                                       @RequestParam(required = false) AlertSeverity severity,
                                       @RequestParam(required = false) String shipmentId) {
        List<Alert> alerts;
        if (status != null) alerts = repository.findAllByStatus(status);
        else if (severity != null) alerts = repository.findAllBySeverity(severity);
        else if (shipmentId != null) alerts = repository.findAllByShipmentCode(shipmentId);
        else alerts = repository.findAll();
        return alerts.stream().map(AlertResourceFromEntityAssembler::toResource).toList();
    }

    @PostMapping("/{alertCode}/acknowledgements")
    public AlertResource acknowledge(@PathVariable String alertCode) {
        return AlertResourceFromEntityAssembler.toResource(commandService.acknowledge(alertCode));
    }

    @PostMapping("/{alertCode}/resolutions")
    public AlertResource resolve(@PathVariable String alertCode) {
        return AlertResourceFromEntityAssembler.toResource(commandService.resolve(alertCode));
    }
}
