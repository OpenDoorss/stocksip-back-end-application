package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Represents the minimum stock of a product in the inventory management system.
 * This value object encapsulates the minimum stock quantity and provides methods to manipulate it.
 *
 * @param minimumStock The minimum stock of the product, must be a non-negative integer.
 */
@Embeddable
public record ProductMinimumStock(int minimumStock) {

    /**
     * This constructor validates the input parameter to ensure that it is a non-negative integer.
     *
     * @param minimumStock The minimum stock of the product.
     * @throws IllegalArgumentException if the minimum stock is negative.
     */
    public ProductMinimumStock {
        if (!isMinimumStockValidate(minimumStock)) {
            throw new IllegalArgumentException("Minimum stock must be a non-negative integer.");
        }
    }

    /**
     * Validates the minimum stock.
     *
     * @param minimumStock The minimum stock of the product.
     * @return true if the minimum stock is a non-negative integer, false otherwise.
     */
    private boolean isMinimumStockValidate(int minimumStock) {
        return !(minimumStock < 0);
    }

    /**
     * Updates the minimum stock of the product.
     *
     * @param newMinimumStock The new minimum stock to be set for the product.
     * @return A new instance of ProductMinimumStock with the updated minimum stock.
     */
    public ProductMinimumStock updateMinimumStock(int newMinimumStock) {
        if (!isMinimumStockValidate(newMinimumStock)) {
            throw new IllegalArgumentException("Updated minimum stock must be a non-negative integer.");
        }
        return new ProductMinimumStock(newMinimumStock);
    }
}
