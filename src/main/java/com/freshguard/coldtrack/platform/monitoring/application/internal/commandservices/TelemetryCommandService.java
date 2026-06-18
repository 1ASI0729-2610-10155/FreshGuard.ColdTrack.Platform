package com.freshguard.coldtrack.platform.monitoring.application.internal.commandservices;

import com.freshguard.coldtrack.platform.alerting.application.internal.commandservices.AlertEvaluationService;
import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.monitoring.domain.model.entities.TelemetryLog;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.SensorRepository;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.TelemetryLogRepository;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.CreateTelemetryReadingResource;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Persists valid telemetry and publishes real-time updates. */
@Service
public class TelemetryCommandService {
    private final SensorRepository sensorRepository;
    private final ShipmentRepository shipmentRepository;
    private final TelemetryLogRepository telemetryRepository;
    private final AlertEvaluationService alertEvaluationService;
    private final SimpMessagingTemplate messagingTemplate;

    public TelemetryCommandService(SensorRepository sensorRepository, ShipmentRepository shipmentRepository,
                                   TelemetryLogRepository telemetryRepository, AlertEvaluationService alertEvaluationService,
                                   SimpMessagingTemplate messagingTemplate) {
        this.sensorRepository = sensorRepository;
        this.shipmentRepository = shipmentRepository;
        this.telemetryRepository = telemetryRepository;
        this.alertEvaluationService = alertEvaluationService;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public TelemetryLog record(CreateTelemetryReadingResource resource) {
        var sensor = sensorRepository.findBySensorCode(resource.sensorCode())
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found: " + resource.sensorCode()));
        if (sensor.getAssignedShipmentCode() == null) throw new ConflictException("Sensor must be assigned before sending telemetry");
        var shipment = shipmentRepository.findByShipmentCode(sensor.getAssignedShipmentCode())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned shipment not found"));
        sensor.record(resource.temperature(), resource.humidity(), resource.recordedAt());
        shipment.updateConditions(resource.temperature(), resource.humidity());
        sensorRepository.save(sensor);
        shipmentRepository.save(shipment);
        var log = telemetryRepository.save(new TelemetryLog(sensor.getSensorCode(), shipment.getShipmentCode(),
                resource.temperature(), resource.humidity(), resource.recordedAt()));
        alertEvaluationService.evaluate(shipment.getShipmentCode(), sensor.getSensorCode(), resource.temperature(), resource.humidity());
        messagingTemplate.convertAndSend("/topic/shipments/" + shipment.getShipmentCode() + "/telemetry", log);
        return log;
    }
}
