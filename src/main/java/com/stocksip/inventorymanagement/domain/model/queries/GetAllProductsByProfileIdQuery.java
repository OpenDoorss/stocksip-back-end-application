package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;

/**
 * GetAllProductsByProfileIdQuery
 *
 * @summary
 * GetAllProductsByProfileIdQuery is a query to retrieve all the products owned by a specific profile.
 *
 * @param profileId The unique identifier of the profile whose products will be retrieved.
 */
public record GetAllProductsByProfileIdQuery(ProfileId profileId) {

    /**
     * Validates the command parameters
     */
    public GetAllProductsByProfileIdQuery {
        if (profileId == null) {
            throw new IllegalArgumentException("Profile Id cannot be null");
        }
    }
}
