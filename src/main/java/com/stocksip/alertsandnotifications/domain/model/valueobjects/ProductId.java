package com.stocksip.alertsandnotifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This record defines the identifier of a product that generates alerts and notifications.
 *
 * @summary
 * Value object that encapsulates the unique identifier for a product,
 * ensuring it is a valid non-empty string.
 *
 * @param productId The unique identifier for the product.
 * @since 1.0
 */
@Embeddable
public record ProductId(String productId) {

    /**
     * Validates the product ID parameter.
     */
    public ProductId {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID must be a non-empty string.");
        }
    }
} 