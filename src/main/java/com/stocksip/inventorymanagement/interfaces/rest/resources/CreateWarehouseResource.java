package com.stocksip.inventorymanagement.interfaces.rest.resources;

import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * Resource record for creating a new warehouse.
 * @summary
 * This record represents the data required to create a new warehouse in the inventory management system.
 *
 * @param name the name of the warehouse
 * @param address the address of the warehouse
 * @param warehouseTemperature the temperature setting of the warehouse
 * @param warehouseCapacity the capacity of the warehouse
 * @since 1.0.0
 */
public record CreateWarehouseResource(String name,
                                      WarehousesAddress address,
                                      WarehouseTemperature warehouseTemperature,
                                      WarehouseCapacity warehouseCapacity) {

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
        if (warehouseTemperature == null) {
            throw new IllegalArgumentException("Temperature cannot be null");
        }
        if (warehouseCapacity == null) {
            throw new IllegalArgumentException("Capacity cannot be null");
        }
    }
}
