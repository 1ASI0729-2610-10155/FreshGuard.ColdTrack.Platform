package com.freshguard.coldtrack.platform.logistics.interfaces.rest.transform;

import com.freshguard.coldtrack.platform.logistics.domain.model.aggregates.Shipment;
import com.freshguard.coldtrack.platform.logistics.interfaces.rest.resources.ShipmentResource;

/** Maps shipment aggregates to REST resources compatible with ColdTrack Front. */
public final class ShipmentResourceFromEntityAssembler {
    private ShipmentResourceFromEntityAssembler() {
    }

    public static ShipmentResource toResource(Shipment shipment) {
        return new ShipmentResource(shipment.getShipmentCode(), shipment.getDestination(), shipment.getStatus(),
                shipment.getDriverName(), shipment.getCargoDescription(), shipment.getCurrentTemperature(),
                shipment.getCurrentHumidity(), shipment.getDepartureAt(), shipment.getEstimatedArrivalAt());
    }
}
