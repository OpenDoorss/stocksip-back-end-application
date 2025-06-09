package com.stocksip.inventorymanagement.interfaces.rest.resources;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * Resource record for creating a new warehouse.
 * @summary
 * This record represents the data required to create a new warehouse in the inventory management system.
 *
 * @param profileId the ID of the profile to which the warehouse belongs
 * @param name the name of the warehouse
 * @param address the address of the warehouse
 * @param temperature the temperature setting of the warehouse
 * @param capacity the capacity of the warehouse
 * @param imageUrl the URL of the image representing the warehouse
 *
 * @since 1.0.0
 */
public record CreateWarehouseResource(ProfileId profileId,
                                      String name,
                                      WarehousesAddress address,
                                      WarehouseTemperature temperature,
                                      WarehouseCapacity capacity,
                                      String imageUrl) {

    /**
     * Validates the resource.
     * @throws IllegalArgumentException if name or image Url are null or blank
     */
    public CreateWarehouseResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image URL cannot be null or blank");
        }
    }
}
