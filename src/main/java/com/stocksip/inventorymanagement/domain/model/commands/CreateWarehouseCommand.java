package com.stocksip.inventorymanagement.domain.model.commands;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

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
                                     WarehousesAddress address,
                                     WarehouseTemperature temperature,
                                     WarehouseCapacity capacity,
                                     String imageUrl) {

    /**
     * Constructor for CreateWarehouseCommand.
     * Validates the input parameters to ensure that the profile ID and warehouse name are not null or blank.
     */
    public CreateWarehouseCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image URL cannot be null or blank");
        }
    }
}
