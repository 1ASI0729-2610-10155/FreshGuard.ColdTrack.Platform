package com.freshguard.coldtrack.platform.monitoring.domain.model.aggregates;

import com.freshguard.coldtrack.platform.monitoring.domain.model.valueobjects.SensorStatus;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {
    @Test
    void shouldPreventConcurrentAssignments() {
        var sensor = new Sensor("SENS-A123");
        sensor.assignTo("ENV-001");

        assertEquals(SensorStatus.ASSIGNED, sensor.getStatus());
        assertThrows(ConflictException.class, () -> sensor.assignTo("ENV-002"));
    }
}
