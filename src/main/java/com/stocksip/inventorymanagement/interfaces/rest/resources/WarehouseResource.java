package com.stocksip.inventorymanagement.interfaces.rest.resources;

import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;

/**
 * WarehouseResource is a record that represents a warehouse resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a warehouse, including its name, address, temperature range,
 * capacity, and an image URL.
 *
 * @since 1.0.0
 */
public record WarehouseResource(String name,
                                WarehousesAddress address,
                                WarehouseTemperature temperature,
                                WarehouseCapacity capacity,
                                String imageUrl) {
}
