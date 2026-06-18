package com.freshguard.coldtrack.platform.engagement.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateContactMessageResource(@NotBlank String fullName, @NotBlank @Email String email,
                                           @NotBlank @Size(max = 1000) String message) { }
