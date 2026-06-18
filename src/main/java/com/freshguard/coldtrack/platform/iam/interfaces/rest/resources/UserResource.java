package com.freshguard.coldtrack.platform.iam.interfaces.rest.resources;

import java.util.Set;

/** Safe public representation of a user account. */
public record UserResource(Long id, String fullName, String email, Set<String> roles) {
}
