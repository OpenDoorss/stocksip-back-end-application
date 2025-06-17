package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * UpdateProductCommand
 *
 * @summary
 * UpdateProductCommand is a record class that represents the command to update a product's details in a warehouse.
 *
 * @param unitPrice The updated unit price of the product, must be greater than or equal to zero.
 * @param minimumStock The updated minimum stock of the product, must be a non-negative integer.
 * @param imageUrl The updated image URL of the product, can be null or empty.
 */
public record UpdateProductCommand(double unitPrice,
                                   int minimumStock,
                                   String imageUrl) {
    /**
     * Validates the command.
     */
    public UpdateProductCommand {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must be greater than or equal to zero.");
        }
        if (minimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock must be a non-negative integer.");
        }
    }
}
