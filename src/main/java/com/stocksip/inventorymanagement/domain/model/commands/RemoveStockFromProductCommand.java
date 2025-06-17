package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * RemoveStockFromProductCommand
 *
 * @summary
 * RemoveStockFromProductCommand is a command used to remove stock from a product in the inventory management system.
 *
 * @param productId The ID of the product from which stock is to be removed.
 * @param removedQuantity The quantity of stock to be removed from the product.
 */
public record RemoveStockFromProductCommand(Long productId, int removedQuantity) {

    /**
     * Validates the command parameters.
     */
    public RemoveStockFromProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (removedQuantity <= 0) {
            throw new IllegalArgumentException("Remove quantity must be greater than zero.");
        }
    }
}
