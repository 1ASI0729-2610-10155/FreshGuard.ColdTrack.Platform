package com.freshguard.coldtrack.platform.analytics.interfaces.rest.resources;

/** Initial report projection; PDF generation will consume this contract. */
public record ShipmentReportResource(String shipmentCode, long telemetryReadings, long alerts,
                                     Double averageTemperature, Double averageHumidity) { }
