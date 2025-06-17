package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * MoveProductToAnotherWarehouseCommand
 *
 * @summary
 * MoveProductToAnotherWarehouseCommand is a record class that represents the command to move a product to another warehouse.
 *
 * @param productId The ID of the product to be moved.
 * @param newWarehouseId The ID of the target warehouse where the product will be moved.
 */
public record MoveProductToAnotherWarehouseCommand(Long productId, Long newWarehouseId) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If product ID or warehouse ID is null
     */
    public MoveProductToAnotherWarehouseCommand {
        if (productId == null || newWarehouseId == null) {
            throw new IllegalArgumentException("Product ID and Warehouse ID cannot be null");
        }
    }
}
