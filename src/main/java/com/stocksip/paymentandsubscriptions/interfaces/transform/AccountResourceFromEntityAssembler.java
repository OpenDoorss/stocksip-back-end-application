package com.stocksip.paymentandsubscriptions.interfaces.transform;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.interfaces.resources.AccountResource;

public class AccountResourceFromEntityAssembler {

    public static AccountResource toResourceFromEntity(Account entity) {
        return new AccountResource(
            entity.getGeneralEmail().email(),
            entity.getRole().role(),
            entity.getPlan().type()
        );
    }
}
