package com.stocksip.inventorymanagement.interfaces.rest.resources;

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
                                double maxTemperature,
                                double minTemperature,
                                double capacity,
                                String address,
                                String imageUrl) {
}
