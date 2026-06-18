package com.freshguard.coldtrack.platform.shared.interfaces.rest;

import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import com.freshguard.coldtrack.platform.shared.interfaces.rest.resources.ErrorResource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

/** Converts application exceptions into consistent localized REST responses. */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResource> handleNotFound(ResourceNotFoundException exception) {
        return response(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "error.not-found", Map.of("reason", exception.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResource> handleConflict(ConflictException exception) {
        return response(HttpStatus.CONFLICT, "RESOURCE_CONFLICT", "error.conflict", Map.of("reason", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResource> handleValidation(MethodArgumentNotValidException exception) {
        var details = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage() == null ? "Invalid value" : error.getDefaultMessage(), (first, second) -> first));
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "error.validation", details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResource> handleUnexpected(Exception exception) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", "error.unexpected", Map.of());
    }

    private ResponseEntity<ErrorResource> response(HttpStatus status, String code, String messageKey, Map<String, String> details) {
        var message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).body(new ErrorResource(Instant.now(), status.value(), code, message, details));
    }
}
