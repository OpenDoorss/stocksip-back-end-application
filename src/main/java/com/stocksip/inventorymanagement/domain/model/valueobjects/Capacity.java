package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This is a value object that represents the total capacity of a warehouse.
 * @summary
 * The WarehouseCapacity class is a record that encapsulates the total capacity of a warehouse in cubic meters.
 *
 * @param capacity The total capacity of the warehouse in cubic meters.
 * @since 1.0.0
 */
@Embeddable
public record Capacity(Double capacity) {

    /**
     * This constructor validates the input parameter to ensure that it is a positive number.
     *
     * @param capacity The total capacity of the warehouse in cubic meters.
     * @throws IllegalArgumentException if the capacity is not a positive number.
     */
    public Capacity {
        validateCapacity(capacity);
    }

    /**
     * Validates the capacity.
     *
     * @param capacity The total capacity of the warehouse in cubic meters.
     * @throws IllegalArgumentException if the capacity is not a positive number.
     */
    private static void validateCapacity(Double capacity) {
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }
    }
}
