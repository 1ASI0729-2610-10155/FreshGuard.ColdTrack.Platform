package com.freshguard.coldtrack.platform.iam.application.internal.commandservices;

import com.freshguard.coldtrack.platform.iam.domain.model.aggregates.UserAccount;
import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import com.freshguard.coldtrack.platform.iam.infrastructure.tokens.jwt.JwtTokenService;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.resources.*;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ConflictException;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/** Executes registration and sign-in commands. */
@Service
public class AuthenticationService {
    private final UserAccountRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;

    public AuthenticationService(UserAccountRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, JwtTokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserResource signUp(SignUpResource resource) {
        if (userRepository.existsByEmailIgnoreCase(resource.email())) {
            throw new ConflictException("A user with this email already exists");
        }
        var role = roleRepository.findByName(resource.role())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        var account = new UserAccount(resource.fullName(), resource.email(), passwordEncoder.encode(resource.password()), Set.of(role));
        return UserResourceFromEntityAssembler.toResource(userRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AuthenticatedUserResource signIn(SignInResource resource) {
        var account = userRepository.findByEmailIgnoreCase(resource.email())
                .orElseThrow(() -> new ResourceNotFoundException("User account not found"));
        if (!passwordEncoder.matches(resource.password(), account.getPasswordHash())) {
            throw new ConflictException("Invalid credentials");
        }
        return new AuthenticatedUserResource(UserResourceFromEntityAssembler.toResource(account), tokenService.generateToken(account.getEmail()), "Bearer");
    }
}
