package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteUpgradeCommand;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CompleteUpgradeResource;

public class CompleteUpgradeFromResourceAssembler {

    public static CompleteUpgradeCommand toCommandFromResource(CompleteUpgradeResource resource) {
        return new CompleteUpgradeCommand(
                resource.token(),
                resource.accountId(),
                resource.planId()
        );
    }
}
