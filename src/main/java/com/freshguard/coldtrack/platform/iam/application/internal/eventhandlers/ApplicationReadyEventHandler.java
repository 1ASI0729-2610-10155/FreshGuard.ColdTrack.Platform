package com.freshguard.coldtrack.platform.iam.application.internal.eventhandlers;

import com.freshguard.coldtrack.platform.iam.domain.model.aggregates.UserAccount;
import com.freshguard.coldtrack.platform.iam.domain.model.entities.Role;
import com.freshguard.coldtrack.platform.iam.domain.model.valueobjects.RoleName;
import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

/** Seeds required roles and the local demonstration account. */
@Component
public class ApplicationReadyEventHandler {
    private final RoleRepository roleRepository;
    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationReadyEventHandler(RoleRepository roleRepository, UserAccountRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seedIdentityData() {
        Arrays.stream(RoleName.values()).forEach(name -> roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(name))));
        if (!userRepository.existsByEmailIgnoreCase("test@test.com")) {
            var adminRole = roleRepository.findByName(RoleName.ROLE_LOGISTICS_ADMIN).orElseThrow();
            userRepository.save(new UserAccount("Demo Logistics Admin", "test@test.com", passwordEncoder.encode("password"), Set.of(adminRole)));
        }
    }
}
