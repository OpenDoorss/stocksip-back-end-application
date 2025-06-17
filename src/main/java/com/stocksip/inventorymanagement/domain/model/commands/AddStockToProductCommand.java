package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * AddStockToProductCommand
 *
 * @summary
 * AddStockToProductCommand is a command used to add stock to a product in the inventory management system.
 *
 * @param productId
 * @param addedQuantity
 */
public record AddStockToProductCommand(Long productId, int addedQuantity) {

    /**
     * Validates the command parameters.
     */
    public AddStockToProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (addedQuantity <= 0) {
            throw new IllegalArgumentException("Added quantity must be greater than zero.");
        }
    }
}
