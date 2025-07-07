package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.UpgradeSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.UpgradeSubscriptionResource;

public class UpgradeSubscriptionFromResourceAssembler {

    public static UpgradeSubscriptionCommand toCommandFromResource(UpgradeSubscriptionResource resource) {
        return new UpgradeSubscriptionCommand(resource.accountId(), resource.planId());
    }
}
