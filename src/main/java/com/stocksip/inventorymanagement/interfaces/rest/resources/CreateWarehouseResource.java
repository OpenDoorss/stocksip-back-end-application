package com.stocksip.inventorymanagement.interfaces.rest.resources;

/**
 * Resource for creating a new warehouse.
 *
 * @param name the name of the warehouse
 * @param street the street address of the warehouse
 * @param city the city where the warehouse is located
 * @param district the district of the warehouse
 * @param postalCode the postal code of the warehouse
 * @param country the country where the warehouse is located
 * @param minTemperature the minimum temperature setting for the warehouse
 * @param maxTemperature the maximum temperature setting for the warehouse
 * @param capacity the capacity of the warehouse in cubic meters
 *
 * @since 1.0.0
 */
public record CreateWarehouseResource(String name,
                                      String street,
                                      String city,
                                      String district,
                                      String postalCode,
                                      String country,
                                      Double minTemperature,
                                      Double maxTemperature,
                                      Double capacity) {

    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the name, address, temperature, capacity is null or invalid.
     */
    public CreateWarehouseResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be null or blank");
        }
    }
}
