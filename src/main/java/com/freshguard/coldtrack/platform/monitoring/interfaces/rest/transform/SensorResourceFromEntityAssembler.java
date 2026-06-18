package com.freshguard.coldtrack.platform.monitoring.interfaces.rest.transform;

import com.freshguard.coldtrack.platform.monitoring.domain.model.aggregates.Sensor;
import com.freshguard.coldtrack.platform.monitoring.interfaces.rest.resources.SensorResource;

public final class SensorResourceFromEntityAssembler {
    private SensorResourceFromEntityAssembler() { }

    public static SensorResource toResource(Sensor sensor) {
        return new SensorResource(sensor.getSensorCode(), sensor.getStatus(), sensor.getAssignedShipmentCode(),
                sensor.getLastReadingAt(), sensor.getTemperature(), sensor.getHumidity());
    }
}
