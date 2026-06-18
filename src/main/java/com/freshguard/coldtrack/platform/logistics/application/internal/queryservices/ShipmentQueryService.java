package com.freshguard.coldtrack.platform.logistics.application.internal.queryservices;

import com.freshguard.coldtrack.platform.logistics.domain.model.aggregates.Shipment;
import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Handles read-only shipment queries. */
@Service
public class ShipmentQueryService {
    private final ShipmentRepository repository;

    public ShipmentQueryService(ShipmentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Shipment> findAll(ShipmentStatus status) {
        return status == null ? repository.findAll() : repository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    public Shipment findByCode(String code) {
        return repository.findByShipmentCode(code).orElseThrow(() -> new ResourceNotFoundException("Shipment not found: " + code));
    }
}
