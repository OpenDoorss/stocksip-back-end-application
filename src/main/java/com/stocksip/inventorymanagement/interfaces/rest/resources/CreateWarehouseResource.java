package com.stocksip.inventorymanagement.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

/**
 * Resource for updating a warehouse.
 * This record holds the details required to update a warehouse's information.
 *
 * @param name          Name of the warehouse
 * @param street        Street address of the warehouse
 * @param city          City where the warehouse is located
 * @param district      District of the warehouse
 * @param postalCode    Postal code of the warehouse location
 * @param country       Country where the warehouse is located
 * @param maxTemperature Maximum temperature allowed in the warehouse
 * @param minTemperature Minimum temperature allowed in the warehouse
 * @param capacity      Total capacity of the warehouse
 * @param image      URL of an image representing the warehouse
 */
public record CreateWarehouseResource(String name,
                                      String street,
                                      String city,
                                      String district,
                                      String postalCode,
                                      String country,
                                      Double maxTemperature,
                                      Double minTemperature,
                                      Double capacity,
                                      MultipartFile image) {

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
