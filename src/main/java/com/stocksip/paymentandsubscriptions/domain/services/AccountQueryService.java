package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAccountByEmailQuery;

import java.util.Optional;

public interface AccountQueryService {
    /**
     * Handles the retrieval of an account by its email address.
     */
    Optional<Account> handle(GetAccountByEmailQuery query);
}
