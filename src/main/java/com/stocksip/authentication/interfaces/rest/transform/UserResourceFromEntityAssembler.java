package com.stocksip.authentication.interfaces.rest.transform;

import com.stocksip.authentication.domain.model.aggregates.User;
import com.stocksip.authentication.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getUserId(), user.getUsername());
    }
}
