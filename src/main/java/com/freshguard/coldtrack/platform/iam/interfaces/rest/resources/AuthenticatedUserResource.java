package com.freshguard.coldtrack.platform.iam.interfaces.rest.resources;

/** Authenticated user and bearer token returned after sign-in. */
public record AuthenticatedUserResource(UserResource user, String token, String tokenType) {
}
