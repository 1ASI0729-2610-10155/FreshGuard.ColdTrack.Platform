package com.freshguard.coldtrack.platform.monitoring.application.internal.commandservices;

import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.monitoring.domain.model.aggregates.Sensor;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.SensorRepository;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.CreateSensorResource;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles registration and shipment assignment of sensors. */
@Service
public class SensorCommandService {
    private final SensorRepository sensorRepository;
    private final ShipmentRepository shipmentRepository;

    public SensorCommandService(SensorRepository sensorRepository, ShipmentRepository shipmentRepository) {
        this.sensorRepository = sensorRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public Sensor create(CreateSensorResource resource) {
        if (sensorRepository.existsBySensorCode(resource.id().toUpperCase())) throw new ConflictException("Sensor code already exists");
        return sensorRepository.save(new Sensor(resource.id()));
    }

    @Transactional
    public Sensor assign(String shipmentCode, String sensorCode) {
        if (shipmentRepository.findByShipmentCode(shipmentCode).isEmpty()) throw new ResourceNotFoundException("Shipment not found: " + shipmentCode);
        var sensor = find(sensorCode);
        sensor.assignTo(shipmentCode);
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor unassign(String sensorCode) {
        var sensor = find(sensorCode);
        sensor.unassign();
        return sensorRepository.save(sensor);
    }

    private Sensor find(String sensorCode) {
        return sensorRepository.findBySensorCode(sensorCode).orElseThrow(() -> new ResourceNotFoundException("Sensor not found: " + sensorCode));
    }
}
