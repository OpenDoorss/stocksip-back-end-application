package com.stocksip.alertsandnotifications.domain.model.queries;

import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;

/**
 * This record defines a query to retrieve all alerts associated with a specific product ID.
 *
 * @summary
 * Query object that encapsulates the information needed to retrieve
 * all alerts associated with a specific product.
 *
 * @param productId The unique identifier of the product for which alerts are being queried.
 * @since 1.0
 */
public record GetAllAlertsByProductIdQuery(ProductId productId) {

    /**
     * Validates the query parameters.
     */
    public GetAllAlertsByProductIdQuery {
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null.");
        }
    }
} 