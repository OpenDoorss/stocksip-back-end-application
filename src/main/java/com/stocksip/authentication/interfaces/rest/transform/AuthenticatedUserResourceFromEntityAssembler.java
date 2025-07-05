package com.stocksip.authentication.interfaces.rest.transform;

import com.stocksip.authentication.domain.model.aggregates.User;
import com.stocksip.authentication.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token, Long accountId) {
        return new AuthenticatedUserResource(user.getUserId(), user.getUsername(), token, accountId);
    }
}
