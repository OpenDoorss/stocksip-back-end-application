package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * Query to retrieve a product exit by its ID.
 * This query is used to fetch a product exit record based on its own ID.
 */
public record GetProductExitByIdQuery(Long productExitId) {

    public GetProductExitByIdQuery {
        if (productExitId == null || productExitId <= 0) {
            throw new IllegalArgumentException("Product exit ID must be a positive number.");
        }
    }
}
