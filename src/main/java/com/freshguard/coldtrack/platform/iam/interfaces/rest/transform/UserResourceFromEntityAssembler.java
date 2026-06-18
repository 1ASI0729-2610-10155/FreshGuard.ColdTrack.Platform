package com.freshguard.coldtrack.platform.iam.interfaces.rest.transform;

import com.freshguard.coldtrack.platform.iam.domain.model.aggregates.UserAccount;
import com.freshguard.coldtrack.platform.iam.interfaces.rest.resources.UserResource;

import java.util.stream.Collectors;

/** Maps IAM aggregates to safe REST resources. */
public final class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {
    }

    public static UserResource toResource(UserAccount account) {
        var roles = account.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        return new UserResource(account.getId(), account.getFullName(), account.getEmail(), roles);
    }
}
