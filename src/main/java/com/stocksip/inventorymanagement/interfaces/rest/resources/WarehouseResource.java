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
                                String street,
                                String city,
                                String district,
                                String postalCode,
                                String country,
                                Double maxTemperature,
                                Double minTemperature,
                                Double capacity,
                                String imageUrl) {
}
