package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * RemoveStockFromProductCommand
 *
 * @summary
 * RemoveStockFromProductCommand is a command used to remove stock from a product in the inventory management system.
 *
 * @param productId The ID of the product from which stock is to be removed.
 * @param warehouseId The ID of the warehouse that stores the product.
 * @param bestBeforeDate The best before date of the product that indicates until when you have time before it lost its properties.
 * @param removedQuantity The quantity of stock to be removed from the product.
 */
public record ReduceStockFromProductCommand(Long productId, Long warehouseId, Date bestBeforeDate, int removedQuantity) {

    /**
     * Validates the command parameters.
     */
    public ReduceStockFromProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse ID cannot be null.");
        }
        if (removedQuantity <= 0) {
            throw new IllegalArgumentException("Remove quantity must be greater than zero.");
        }
    }
}
