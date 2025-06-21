package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * UpdateProductMinimumStockCommand
 *
 * @summary
 * UpdateProductMinimumStockCommand is a command used to update a product's minimum stock level in a warehouse.
 *
 * @param productId The ID of the product which its minimum stock level will be updated.
 * @param minimumStock The new minimum stock level of the product.
 */
public record UpdateProductMinimumStockCommand(Long productId, int minimumStock) {

    /**
     * Validates the command parameters.
     */
    public UpdateProductMinimumStockCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (minimumStock <= 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative.");
        }
    }
}
