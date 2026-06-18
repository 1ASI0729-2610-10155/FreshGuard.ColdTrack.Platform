package com.freshguard.coldtrack.platform.logistics.interfaces.rest;

import com.freshguard.coldtrack.platform.logistics.application.internal.commandservices.ShipmentCommandService;
import com.freshguard.coldtrack.platform.logistics.application.internal.queryservices.ShipmentQueryService;
import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources.CreateShipmentResource;
import com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources.ShipmentResource;
import com.freshguard.coldtrack.platform.logistics.interfaces.rest.transform.ShipmentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Exposes shipment registration, queries and lifecycle actions. */
@RestController
@RequestMapping("/api/v1/shipments")
@Tag(name = "Shipments")
public class ShipmentsController {
    private final ShipmentCommandService commandService;
    private final ShipmentQueryService queryService;

    public ShipmentsController(ShipmentCommandService commandService, ShipmentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Register a shipment")
    public ResponseEntity<ShipmentResource> create(@Valid @RequestBody CreateShipmentResource resource) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ShipmentResourceFromEntityAssembler.toResource(commandService.create(resource)));
    }

    @GetMapping
    @Operation(summary = "List shipments with an optional status filter")
    public List<ShipmentResource> findAll(@RequestParam(required = false) ShipmentStatus status) {
        return queryService.findAll(status).stream().map(ShipmentResourceFromEntityAssembler::toResource).toList();
    }

    @GetMapping("/{shipmentCode}")
    public ShipmentResource findByCode(@PathVariable String shipmentCode) {
        return ShipmentResourceFromEntityAssembler.toResource(queryService.findByCode(shipmentCode));
    }

    @PostMapping("/{shipmentCode}/departures")
    public ShipmentResource start(@PathVariable String shipmentCode) {
        return ShipmentResourceFromEntityAssembler.toResource(commandService.start(shipmentCode));
    }

    @PostMapping("/{shipmentCode}/completions")
    public ShipmentResource complete(@PathVariable String shipmentCode) {
        return ShipmentResourceFromEntityAssembler.toResource(commandService.complete(shipmentCode));
    }

    @PostMapping("/{shipmentCode}/cancellations")
    public ShipmentResource cancel(@PathVariable String shipmentCode) {
        return ShipmentResourceFromEntityAssembler.toResource(commandService.cancel(shipmentCode));
    }
}
