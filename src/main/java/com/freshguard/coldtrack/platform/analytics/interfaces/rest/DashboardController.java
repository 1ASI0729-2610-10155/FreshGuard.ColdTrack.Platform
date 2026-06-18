package com.freshguard.coldtrack.platform.analytics.interfaces.rest;

import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertSeverity;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.AlertStatus;
import com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.freshguard.coldtrack.platform.analytics.interfaces.rest.resources.DashboardResource;
import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import com.freshguard.coldtrack.platform.logistics.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import com.freshguard.coldtrack.platform.monitoring.domain.model.valueobjects.SensorStatus;
import com.freshguard.coldtrack.platform.monitoring.infrastructure.persistence.jpa.repositories.SensorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class DashboardController {
    private final ShipmentRepository shipments;
    private final SensorRepository sensors;
    private final AlertRepository alerts;

    public DashboardController(ShipmentRepository shipments, SensorRepository sensors, AlertRepository alerts) {
        this.shipments = shipments;
        this.sensors = sensors;
        this.alerts = alerts;
    }

    @GetMapping("/dashboard")
    public DashboardResource dashboard() {
        return new DashboardResource(shipments.count(), shipments.countByStatus(ShipmentStatus.IN_TRANSIT),
                shipments.countByStatus(ShipmentStatus.COMPLETED), sensors.count(), sensors.countByStatus(SensorStatus.AVAILABLE),
                alerts.countByStatus(AlertStatus.ACTIVE), alerts.countBySeverity(AlertSeverity.CRITICAL));
    }
}
