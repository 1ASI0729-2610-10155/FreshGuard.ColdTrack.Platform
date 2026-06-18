package com.freshguard.coldtrack.platform.logistics.application.internal.commandservices;

import com.freshguard.coldtrack.platform.logistics.domain.model.aggregates.Shipment;
import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources.CreateShipmentResource;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles commands that change shipment state. */
@Service
public class ShipmentCommandService {
    private final ShipmentRepository repository;

    public ShipmentCommandService(ShipmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Shipment create(CreateShipmentResource resource) {
        return repository.save(new Shipment(resource.destination(), resource.driver(), resource.cargoDescription(), resource.departureAt(), resource.estimatedArrivalAt()));
    }

    @Transactional
    public Shipment start(String code) {
        var shipment = find(code);
        shipment.start();
        return repository.save(shipment);
    }

    @Transactional
    public Shipment complete(String code) {
        var shipment = find(code);
        shipment.complete();
        return repository.save(shipment);
    }

    @Transactional
    public Shipment cancel(String code) {
        var shipment = find(code);
        shipment.cancel();
        return repository.save(shipment);
    }

    private Shipment find(String code) {
        return repository.findByShipmentCode(code).orElseThrow(() -> new ResourceNotFoundException("Shipment not found: " + code));
    }
}
