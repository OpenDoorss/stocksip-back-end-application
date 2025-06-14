package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;

/**
 * Query to retrieve all warehouses associated with a specific profile ID.
 *
 * @summary
 * This query is used to fetch all warehouses that belong to a given profile, identified by its profile ID.
 * It is typically used in scenarios where a user needs to view or manage all warehouses linked to their profile.
 *
 * @since 1.0.0
 */
public record GetAllWarehousesByProfileIdQuery(ProfileId profileId) {
}
