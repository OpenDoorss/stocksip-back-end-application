package com.stocksip.inventorymanagement.interfaces.rest.resources;

import com.stocksip.inventorymanagement.domain.model.valueobjects.Capacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.Temperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * Resource record for creating a new warehouse.
 * @summary
 * This record represents the data required to create a new warehouse in the inventory management system.
 *
 * @param name the name of the warehouse
 * @param address the address of the warehouse
 * @param temperature the temperature setting of the warehouse
 * @param capacity the capacity of the warehouse
 * @since 1.0.0
 */
public record CreateWarehouseResource(String name,
                                      WarehousesAddress address,
                                      Temperature temperature,
                                      Capacity capacity) {

    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the name, address, temperature, capacity is null or invalid.
     */
    public CreateWarehouseResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (temperature == null) {
            throw new IllegalArgumentException("Temperature cannot be null");
        }
        if (capacity == null) {
            throw new IllegalArgumentException("Capacity cannot be null");
        }
    }
}
