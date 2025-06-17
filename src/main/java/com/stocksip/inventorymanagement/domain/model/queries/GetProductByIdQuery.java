package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * GetProductByIdQuery
 *
 * @summary
 * GetProductByIdQuery is a query object used to retrieve a product by its ID.
 *
 * @param productId The ID of the product to retrieve.
 */
public record GetProductByIdQuery(Long productId) {

    /**
     * Validates the product ID of the query.
     */
    public GetProductByIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
    }
}
