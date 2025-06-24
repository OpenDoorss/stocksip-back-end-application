package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * Query to retrieve all product exits associated with a specific warehouse.
 * This query is used to fetch product exit records based on the warehouse ID.
 */
public record GetAllProductExitsByProductIdQuery(Long productId) {

    /**
     * Constructs a new GetAllProductExitsByProductIdQuery.
     *
     * @param productId the ID of the product for which to retrieve exits.
     * @throws IllegalArgumentException if the productId is null or not positive.
     */
    public GetAllProductExitsByProductIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
    }
}
