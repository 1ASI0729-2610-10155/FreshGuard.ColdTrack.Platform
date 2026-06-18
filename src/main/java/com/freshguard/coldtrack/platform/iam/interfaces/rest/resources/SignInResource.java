package com.freshguard.coldtrack.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Credentials accepted by the sign-in endpoint. */
public record SignInResource(@NotBlank @Email String email, @NotBlank String password) {
}
