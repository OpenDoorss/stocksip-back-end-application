package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * CreateProductMovementCommand
 *
 * @summary
 * CreateProductMovementCommand is a record class that represents the command to register a product movement in a warehouse.
 */
public record CreateProductMovementCommand(Long productId) {
    /**
     * Validation of the command
     *
     * @throws IllegalArgumentException if the product ID is null or empty.
     */
    public CreateProductMovementCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}
