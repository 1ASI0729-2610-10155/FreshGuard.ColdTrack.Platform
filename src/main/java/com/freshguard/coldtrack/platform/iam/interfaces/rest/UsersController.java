package com.freshguard.coldtrack.platform.iam.interfaces.rest;

import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.resources.UserResource;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UserAccountRepository repository;

    public UsersController(UserAccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<UserResource> findAll() {
        return repository.findAll().stream().map(UserResourceFromEntityAssembler::toResource).toList();
    }

    @GetMapping("/{userId}")
    public UserResource findById(@PathVariable Long userId) {
        return repository.findById(userId).map(UserResourceFromEntityAssembler::toResource)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
