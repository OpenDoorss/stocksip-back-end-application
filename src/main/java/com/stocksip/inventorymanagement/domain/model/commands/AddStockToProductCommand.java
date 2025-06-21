package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * AddStockToProductCommand
 *
 * @summary
 * AddStockToProductCommand is a command used to add stock to a product in the inventory management system.
 *
 * @param productId The ID of the product that will be stored in a new warehouse.
 * @param warehouseId The ID of the warehouse that will store the product.
 * @param bestBeforeDate The best before date of the product that indicates until when you have time before it lost its properties.
 * @param addedQuantity The quantity of product stock to add to the warehouse.
 */
public record AddStockToProductCommand(Long productId, Long warehouseId, Date bestBeforeDate, int addedQuantity) {

    /**
     * Validates the command parameters.
     */
    public AddStockToProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse ID cannot be null.");
        }
        if (bestBeforeDate == null || bestBeforeDate.before(new Date()) ) {
            throw new IllegalArgumentException("BestBeforeDate cannot be null or a past date.");
        }
        if (addedQuantity <= 0) {
            throw new IllegalArgumentException("Added quantity must be greater than zero.");
        }
    }
}
