package com.stocksip.authentication.interfaces.rest.transform;

import com.stocksip.authentication.domain.model.commands.SignInCommand;
import com.stocksip.authentication.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
