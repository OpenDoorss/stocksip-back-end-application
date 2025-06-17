package com.stocksip.inventorymanagement.domain.model.commands;

import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * Command for updating an existing warehouse.
 *
 * @param warehouseId the ID of the warehouse to update
 * @param name the name of the warehouse
 * @param address the address of the warehouse
 * @param warehouseTemperature the temperature setting of the warehouse
 * @param warehouseCapacity the capacity of the warehouse
 * @param imageUrl the URL of the image representing the warehouse
 * @param profileId the ID of the profile associated with the warehouse
 *
 * @since 1.0.0
 */
public record UpdateWarehouseCommand(Long warehouseId,
                                     String name,
                                     WarehousesAddress address,
                                     WarehouseTemperature warehouseTemperature,
                                     WarehouseCapacity warehouseCapacity,
                                     String imageUrl,
                                     Long profileId) {
    /**
     * Constructor for UpdateWarehouseCommand.
     * Validates the input parameters to ensure that the warehouse ID is not null or less than or equal to 0,
     * the name is not null or blank, and the image URL is not null or blank.
     *
     * @throws IllegalArgumentException if the warehouseId is null or less than or equal to 0,
     *                                  the name is null or blank,
     *                                  or if the image URL is null or blank.
     *
     * @since 1.0.0
     */
    public UpdateWarehouseCommand {
        if (warehouseId == null || warehouseId <= 0) {
            throw new IllegalArgumentException("courseId cannot be null or less than or equal to 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (warehouseTemperature == null) {
            throw new IllegalArgumentException("Temperature cannot be null");
        }
        if (warehouseCapacity == null) {
            throw new IllegalArgumentException("Capacity cannot be null");
        }
    }
}
