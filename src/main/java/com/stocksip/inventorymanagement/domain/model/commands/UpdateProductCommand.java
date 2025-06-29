package com.stocksip.inventorymanagement.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

/**
 * UpdateProductCommand
 *
 * @summary
 * UpdateProductCommand is a record class that represents the command to update a product's details in a warehouse.
 *
 * @param productId The ID of the product that will be updated.
 * @param unitPriceAmount The updated unit price of the product must be greater than or equal to zero.
 * @param minimumStock The updated minimum stock of the product must be a non-negative integer.
 * @param image The updated image file for the product can be null if no image is provided.
 */
public record UpdateProductCommand(Long productId, double unitPriceAmount, int minimumStock, MultipartFile image) {

    /**
     * Validates the command.
     */
    public UpdateProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (unitPriceAmount <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than or equal to zero.");
        }
        if (minimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock must be a non-negative integer.");
        }
    }
}
