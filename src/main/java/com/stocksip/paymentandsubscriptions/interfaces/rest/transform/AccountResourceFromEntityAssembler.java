package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.AccountResource;

public class AccountResourceFromEntityAssembler {

    public static AccountResource toResourceFromEntity(Account entity) {
        return new AccountResource(
                entity.getAccountId(),
                entity.getBusinessName().value(),
                entity.getStatus().name(),
                entity.getAccountRole().name(),
                entity.GetCreatedAt().toString()
        );
    }

}
