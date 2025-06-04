package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * CreateWarehouseCommand
 *
 * @summary
 * CreateWarehouseCommand is a record class that represents the command to create a warehouse.
 */
public record CreateWarehouseCommand(Long id,
                                     String name,
                                     String location,
                                     double warehouseSize,
                                     Long userId)
{
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If user ID is null
     */
    public CreateWarehouseCommand {
        if (userId == null)
            throw new IllegalArgumentException("User ID cannot be null or empty");
    }
}
