package com.stocksip.alertsandnotifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This record defines the identifier of a warehouse that stores a product that generates alerts and notifications.
 *
 * @summary
 * Value object that encapsulates the unique identifier for a warehouse,
 * ensuring it is a valid non-empty string.
 *
 * @param warehouseId The unique identifier for the warehouse.
 * @since 1.0
 */
@Embeddable
public record WarehouseId(String warehouseId) {

    /**
     * Validates the warehouse ID parameter.
     */
    public WarehouseId {
        if (warehouseId == null || warehouseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse ID must be a non-empty string.");
        }
    }
} 