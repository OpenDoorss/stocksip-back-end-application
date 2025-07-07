package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CompleteSubscriptionResource;

public class CompleteSubscriptionFromResourceAssembler {

    public static CompleteSubscriptionCommand toCommandFromResource(CompleteSubscriptionResource resource) {
        return new CompleteSubscriptionCommand(
                resource.token(),
                resource.accountId(),
                resource.planId()
        );
    }
}
