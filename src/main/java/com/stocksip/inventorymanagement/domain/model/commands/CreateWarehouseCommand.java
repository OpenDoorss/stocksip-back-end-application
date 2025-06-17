package com.stocksip.inventorymanagement.domain.model.commands;

import com.stocksip.inventorymanagement.domain.model.valueobjects.*;

/**
 * Command to create a new warehouse.
 *
 * @summary
 * This command encapsulates the necessary information to create a new warehouse in the inventory management system.
 * It includes the profile ID of the owner, the name of the warehouse, its address, temperature range, capacity, and an image URL.
 *
 * @since 1.0.0
 */
public record CreateWarehouseCommand(String name,
                                     String street,
                                     String city,
                                     String district,
                                     String postalCode,
                                     String country,
                                     Double maxTemperature,
                                     Double minTemperature,
                                     Double capacity,
                                     String imageUrl,
                                     Long profileId) {

    /**
     * Constructor for CreateWarehouseCommand.
     * Validates the input parameters to ensure that the warehouse name is not null or blank.
     */
    public CreateWarehouseCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
    }
}
