package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProviderId;

/**
 * GetAllProductsByProviderIdQuery
 *
 * @summary
 * GetAllProductsByProviderIdQuery is a query to retrieve all products associated with a specific provider.
 *
 * @param providerId The unique identifier of the provider whose provided products are to be retrieved.
 */
public record GetAllProductsByProviderIdQuery(ProviderId providerId) {

    /**
     * Validates the query parameters.
     */
    public GetAllProductsByProviderIdQuery {
        if (providerId == null) {
            throw new IllegalArgumentException("ProviderId cannot be null");
        }
    }
}
