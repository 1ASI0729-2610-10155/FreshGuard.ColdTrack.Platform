package com.freshguard.coldtrack.platform.shared.interfaces.rest.resources;

import java.time.Instant;
import java.util.Map;

/** Standard error payload returned by the REST API. */
public record ErrorResource(
        Instant timestamp,
        int status,
        String code,
        String message,
        Map<String, String> details) {
}
