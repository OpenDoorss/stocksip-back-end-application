package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * DeleteProductFromWarehouseCommand
 *
 * @summary
 * DeleteProductFromWarehouseCommand is a command used to delete a product that has no stock from a warehouse.
 *
 * @param productId
 * @param warehouseId
 * @param bestBeforeDate
 */
public record DeleteProductFromWarehouseCommand(Long productId, Long warehouseId, Date bestBeforeDate) {

    public DeleteProductFromWarehouseCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse ID cannot be null.");
        }
        if (bestBeforeDate == null || bestBeforeDate.before(new Date()) ) {
            throw new IllegalArgumentException("BestBeforeDate cannot be null or a past date.");
        }
    }
}
