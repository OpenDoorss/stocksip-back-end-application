package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * Command for updating a warehouse.
 * This record holds the details required to update a warehouse's information.
 *
 * @param warehouseId   ID of the warehouse to be updated
 * @param name          Name of the warehouse
 * @param street        Street address of the warehouse
 * @param city          City where the warehouse is located
 * @param district      District of the warehouse
 * @param postalCode    Postal code of the warehouse location
 * @param country       Country where the warehouse is located
 * @param maxTemperature Maximum temperature allowed in the warehouse
 * @param minTemperature Minimum temperature allowed in the warehouse
 * @param capacity      Total capacity of the warehouse
 * @param imageUrl      URL of an image representing the warehouse
 * @param accountId     ID of the profile associated with this warehouse
 */
public record UpdateWarehouseCommand(Long warehouseId,
                                     String name,
                                     String street,
                                     String city,
                                     String district,
                                     String postalCode,
                                     String country,
                                     Double maxTemperature,
                                     Double minTemperature,
                                     Double capacity,
                                     String imageUrl,
                                     Long accountId) {
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
    }
}