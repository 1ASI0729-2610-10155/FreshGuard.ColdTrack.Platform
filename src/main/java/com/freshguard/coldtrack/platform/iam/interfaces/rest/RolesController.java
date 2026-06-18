package com.freshguard.coldtrack.platform.iam.interfaces.rest;

import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.resources.RoleResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {
    private final RoleRepository repository;

    public RolesController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RoleResource> findAll() {
        return repository.findAll().stream().map(role -> new RoleResource(role.getId(), role.getName().name())).toList();
    }
}
