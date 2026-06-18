package com.freshguard.coldtrack.platform.profiles.interfaces.rest;

import com.freshguard.coldtrack.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.freshguard.coldtrack.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfilesController {
    private final ProfileRepository repository;

    public ProfilesController(ProfileRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProfileResource> findAll() {
        return repository.findAll().stream().map(this::toResource).toList();
    }

    @GetMapping("/{profileId}")
    public ProfileResource findById(@PathVariable Long profileId) {
        return repository.findById(profileId).map(this::toResource)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    private ProfileResource toResource(com.freshguard.coldtrack.platform.profiles.domain.model.aggregates.Profile profile) {
        return new ProfileResource(profile.getId(), profile.getUserId(), profile.getFullName(), profile.getEmail(), profile.getPhoneNumber());
    }
}
