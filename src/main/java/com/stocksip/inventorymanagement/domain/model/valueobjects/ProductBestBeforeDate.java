package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Date;

/**
 * Represents the best before date of a product in the inventory management system.
 * This value object encapsulates the date and provides validation to ensure it is a future date.
 *
 * @param date The best before date of the product, must be a future date.
 */
@Embeddable
public record ProductBestBeforeDate(Date date) {

    /**
     * This constructor validates the input parameter to ensure that it is a future date.
     *
     * @param date The best before date of the product.
     * @throws IllegalArgumentException if the date is null or not a future date.
     */
    public ProductBestBeforeDate {
        if (date == null || date.before(new Date())) {
            throw new IllegalArgumentException("Best before date must be a future date.");
        }
    }
}
