package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * AddProductsToWarehouseCommand
 *
 * @summary
 * AddProductsToWarehouseCommand is a command used to add products to a warehouse in the inventory management system.
 *
 * @param productId The ID of the product that will be stored in a new warehouse.
 * @param warehouseId The ID of the warehouse that will store the product.
 * @param bestBeforeDate The best before date of the product that indicates until when you have time before it lost its properties.
 * @param quantity The quantity of product stock to add to the warehouse.
 */
public record AddProductsToWarehouseCommand(Long productId, Long warehouseId, Date bestBeforeDate, int quantity) {

    /**
     * Validates the command parameters.
     */
    public AddProductsToWarehouseCommand {
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("WarehouseId cannot be null");
        }
        if (bestBeforeDate == null || bestBeforeDate.before(new Date()) ) {
            throw new IllegalArgumentException("BestBeforeDate cannot be null or a past date.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
    }
}
