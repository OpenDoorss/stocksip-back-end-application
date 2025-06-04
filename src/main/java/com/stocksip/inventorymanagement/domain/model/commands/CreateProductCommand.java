package com.stocksip.inventorymanagement.domain.model.commands;

import java.util.Date;

/**
 * CreateProductCommand
 *
 * @summary
 * CreateProductCommand is a record class that represents the command to register a product in a warehouse.
 */
public record CreateProductCommand(Long id,
                                   String name,
                                   String liquorType,
                                   Date expirationDate,
                                   double price,
                                   int currentStock,
                                   int minimumStock,
                                   Long warehouseId,
                                   Long providerId)
{
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If warehouse ID is null
     */
    public CreateProductCommand {
        if (warehouseId == null)
            throw new IllegalArgumentException("Warehouse ID cannot be null or empty");
    }
}
