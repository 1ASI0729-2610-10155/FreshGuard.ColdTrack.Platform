package com.freshguard.coldtrack.platform.analytics.interfaces.rest.resources;

/** Aggregated operational metrics displayed by the dashboard. */
public record DashboardResource(long totalShipments, long activeShipments, long completedShipments,
                                long totalSensors, long availableSensors, long activeAlerts, long criticalAlerts) { }
