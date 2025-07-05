package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CreateAccountResource;

public class CreateAccountCommandFromResourceAssembler {

    public static CreateAccountCommand toCommandFromResource(CreateAccountResource resource) {
        return new CreateAccountCommand(
            resource.username(),
            resource.password(),
            resource.validatePassword(),
            resource.accountRole(),
            resource.businessName()
        );
    }
}
