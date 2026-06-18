package com.freshguard.coldtrack.platform.shared.domain.exceptions;

/** Raised when a requested domain resource does not exist. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
