package com.stocksip.paymentandsubscriptions.interfaces.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.interfaces.resources.CreateAccountResource;

public class CreateAccountCommandFromResourceAssembler {

    public static CreateAccountCommand toCommandFromResource(CreateAccountResource resource) {
        return new CreateAccountCommand(
            resource.email(),
            resource.userOwnerId(),
            resource.role(),
            resource.plan(),
            resource.businessName()
        );
    }
}
