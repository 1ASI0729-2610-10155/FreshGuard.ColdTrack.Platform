package com.freshguard.coldtrack.platform.profiles.interfaces.rest.resources;

public record ProfileResource(Long id, Long userId, String fullName, String email, String phoneNumber) { }
