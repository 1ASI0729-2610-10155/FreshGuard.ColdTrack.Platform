package com.freshguard.coldtrack.platform.iam.interfaces.rest.resources;

import com.freshguard.coldtrack.platform.iam.domain.model.valueobjects.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Data required to register a ColdTrack user. */
public record SignUpResource(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotNull RoleName role) {
}
