package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * MoveProductToAnotherWarehouseCommand
 *
 * @summary
 * MoveProductToAnotherWarehouseCommand is a record class that represents the command to move a product to another warehouse.
 *
 * @param productId The ID of the product to be moved.
 * @param oldWarehouseId The ID of the original warehouse where the product stock was store.
 * @param newWarehouseId The ID of the target warehouse where the product will be moved.
 * @param bestBeforeDate The best before date of the product that indicates until when you have time before it lost its properties.
 * @param quantityToMove The quantity of product stock to be moved to another warehouse.
 */
public record MoveProductToAnotherWarehouseCommand(Long productId, Long oldWarehouseId, Long newWarehouseId, Date bestBeforeDate, int quantityToMove) {

    /**
     * Validates the command.
     */
    public MoveProductToAnotherWarehouseCommand {
        if (productId == null || newWarehouseId == null) {
            throw new IllegalArgumentException("Product ID and Warehouse ID cannot be null");
        }
    }
}
