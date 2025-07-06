package com.stocksip.authentication.interfaces.rest.transform;

import com.stocksip.authentication.domain.model.commands.SignUpCommand;
import com.stocksip.authentication.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.password());
    }
}
