package com.freshguard.coldtrack.platform.alerting.interfaces.rest.transform;

import com.freshguard.coldtrack.platform.alerting.domain.model.aggregates.Alert;
import com.freshguard.coldtrack.platform.alerting.interfaces.rest.resources.AlertResource;

public final class AlertResourceFromEntityAssembler {
    private AlertResourceFromEntityAssembler() { }

    public static AlertResource toResource(Alert alert) {
        return new AlertResource(alert.getAlertCode(), alert.getSeverity(), alert.getStatus(), alert.getType(),
                alert.getShipmentCode(), alert.getSensorCode(), alert.getMessage(), alert.getTriggeredAt(),
                alert.getMeasuredValue(), alert.getThresholdValue());
    }
}
