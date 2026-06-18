package com.freshguard.coldtrack.platform.shared.domain.exceptions;

/** Raised when an operation conflicts with the current domain state. */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
