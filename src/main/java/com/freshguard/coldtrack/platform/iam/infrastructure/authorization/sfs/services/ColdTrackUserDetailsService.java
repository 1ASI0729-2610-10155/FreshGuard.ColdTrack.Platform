package com.freshguard.coldtrack.platform.iam.infrastructure.authorization.sfs.services;

import com.freshguard.coldtrack.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Adapts ColdTrack user accounts to Spring Security user details. */
@Service
public class ColdTrackUserDetailsService implements UserDetailsService {
    private final UserAccountRepository repository;

    public ColdTrackUserDetailsService(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var account = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User account not found"));
        var authorities = account.getRoles().stream().map(role -> role.getName().name()).toArray(String[]::new);
        return User.withUsername(account.getEmail()).password(account.getPasswordHash()).authorities(authorities)
                .disabled(!account.isActive()).build();
    }
}
