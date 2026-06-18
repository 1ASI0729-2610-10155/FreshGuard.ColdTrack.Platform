package com.freshguard.coldtrack.platform.iam.interfaces.rest;

import com.freshguard.coldtrack.platform.iam.application.internal.commandservices.AuthenticationService;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.resources.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Exposes registration and authentication operations. */
@RestController
@RequestMapping("/api/v1/authentication")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Register a user account")
    public ResponseEntity<UserResource> signUp(@Valid @RequestBody SignUpResource resource) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.signUp(resource));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Authenticate a user and obtain a JWT")
    public ResponseEntity<AuthenticatedUserResource> signIn(@Valid @RequestBody SignInResource resource) {
        return ResponseEntity.ok(service.signIn(resource));
    }
}
