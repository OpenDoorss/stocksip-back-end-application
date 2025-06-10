package com.stocksip.inventorymanagement.interfaces.rest.resources;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * Resource for updating an existing warehouse.
 *
 * @param name the name of the warehouse
 * @param address the address of the warehouse
 * @param temperature the temperature setting of the warehouse
 * @param capacity the capacity of the warehouse
 * @param imageUrl the URL of the image representing the warehouse
 *
 * @since 1.0.0
 */
public record UpdateWarehouseResource(String name,
                                      WarehousesAddress address,
                                      WarehouseTemperature temperature,
                                      WarehouseCapacity capacity,
                                      String imageUrl) {

    /**
     * Constructor for UpdateWarehouseResource.
     * Validates the input parameters to ensure that the name is not null or blank.
     */
    public UpdateWarehouseResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image URL cannot be null or blank");
        }
    }
}
