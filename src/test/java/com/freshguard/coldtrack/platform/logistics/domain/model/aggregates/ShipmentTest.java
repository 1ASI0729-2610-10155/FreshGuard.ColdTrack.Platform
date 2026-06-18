package com.freshguard.coldtrack.platform.logistics.domain.model.aggregates;

import com.freshguard.coldtrack.platform.logistics.domain.model.valueobjects.ShipmentStatus;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {
    @Test
    void shouldFollowTheHappyPathLifecycle() {
        var departure = LocalDateTime.now().plusDays(1);
        var shipment = new Shipment("Lima", "Carlos Ruiz", "Fresh produce", departure, departure.plusHours(8));

        assertEquals(ShipmentStatus.REGISTERED, shipment.getStatus());
        shipment.start();
        assertEquals(ShipmentStatus.IN_TRANSIT, shipment.getStatus());
        shipment.complete();
        assertEquals(ShipmentStatus.COMPLETED, shipment.getStatus());
        assertNotNull(shipment.getCompletedAt());
    }

    @Test
    void shouldRejectAnInvalidArrivalTime() {
        var departure = LocalDateTime.now().plusDays(1);
        assertThrows(ConflictException.class,
                () -> new Shipment("Lima", "Carlos Ruiz", "Fresh produce", departure, departure.minusHours(1)));
    }
}
