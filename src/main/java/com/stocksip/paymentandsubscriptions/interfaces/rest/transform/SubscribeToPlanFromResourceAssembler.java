package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.SubscribeToPlanCommand;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.SubscribeToPlanResource;

public class SubscribeToPlanFromResourceAssembler {

    public static SubscribeToPlanCommand toCommandFromResource(SubscribeToPlanResource resource) {
        return new SubscribeToPlanCommand(resource.accountId(), resource.planId());
    }
}
