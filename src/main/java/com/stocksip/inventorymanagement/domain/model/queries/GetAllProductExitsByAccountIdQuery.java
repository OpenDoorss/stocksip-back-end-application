package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;

/**
 * Query to retrieve all product exits associated with a specific account ID.
 */
public record GetAllProductExitsByAccountIdQuery(AccountId accountId) {

    /**
     * Constructs a query to get all product exits by account ID.
     *
     * @param accountId the ID of the account for which to retrieve product exits
     * @throws IllegalArgumentException if accountId is null
     */
    public GetAllProductExitsByAccountIdQuery {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
    }
}
