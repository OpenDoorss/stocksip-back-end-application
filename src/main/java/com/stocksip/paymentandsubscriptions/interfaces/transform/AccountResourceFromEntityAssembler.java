package com.stocksip.paymentandsubscriptions.interfaces.transform;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.interfaces.resources.AccountResource;

public class AccountResourceFromEntityAssembler {

    public static AccountResource toResourceFromEntity(Account acc) {
        return new AccountResource(
                acc.getAccountId(),
                acc.getEmail().value(),
                acc.getUserOwnerId().userId(),
                acc.getRole().value(),
                acc.getBusinessName().value()
        );
    }

}
