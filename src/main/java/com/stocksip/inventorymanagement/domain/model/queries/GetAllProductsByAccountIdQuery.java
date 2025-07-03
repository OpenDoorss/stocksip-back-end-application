package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;

/**
 * GetAllProductsByProfileIdQuery
 *
 * @summary
 * GetAllProductsByProfileIdQuery is a query to retrieve all the products owned by a specific profile.
 *
 * @param accountId The unique identifier of the profile whose products will be retrieved.
 */
public record GetAllProductsByAccountIdQuery(AccountId accountId) {

    /**
     * Validates the command parameters
     */
    public GetAllProductsByAccountIdQuery {
        if (accountId == null) {
            throw new IllegalArgumentException("Profile Id cannot be null");
        }
    }
}
