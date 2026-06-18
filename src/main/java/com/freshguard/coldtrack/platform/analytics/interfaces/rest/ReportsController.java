package com.freshguard.coldtrack.platform.analytics.interfaces.rest;

import com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.freshguard.coldtrack.platform.analytics.interfaces.rest.resources.ShipmentReportResource;
import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.TelemetryLogRepository;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {
    private final ShipmentRepository shipments;
    private final TelemetryLogRepository telemetry;
    private final AlertRepository alerts;

    public ReportsController(ShipmentRepository shipments, TelemetryLogRepository telemetry, AlertRepository alerts) {
        this.shipments = shipments;
        this.telemetry = telemetry;
        this.alerts = alerts;
    }

    @GetMapping("/shipments/{shipmentCode}")
    public ShipmentReportResource shipmentReport(@PathVariable String shipmentCode) {
        if (shipments.findByShipmentCode(shipmentCode).isEmpty()) throw new ResourceNotFoundException("Shipment not found: " + shipmentCode);
        var readings = telemetry.findAllByShipmentCodeOrderByRecordedAtAsc(shipmentCode);
        var averageTemperature = readings.stream().mapToDouble(item -> item.getTemperature()).average().stream().boxed().findFirst().orElse(null);
        var averageHumidity = readings.stream().mapToDouble(item -> item.getHumidity()).average().stream().boxed().findFirst().orElse(null);
        return new ShipmentReportResource(shipmentCode, readings.size(), alerts.findAllByShipmentCode(shipmentCode).size(), averageTemperature, averageHumidity);
    }
}
