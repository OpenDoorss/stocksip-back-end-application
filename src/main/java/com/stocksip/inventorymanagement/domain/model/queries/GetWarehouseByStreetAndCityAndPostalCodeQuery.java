package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * Query to retrieve a warehouse by its street, city, and postal code.
 * This query is used to find a specific warehouse based on its address details.
 * @param street The street address of the warehouse.
 * @param city The city address of the warehouse.
 * @param postalCode The postal code of the warehouse.
 *
 * @since 1.0.0
 */
public record GetWarehouseByStreetAndCityAndPostalCodeQuery(String street,
                                                            String city,
                                                            String postalCode) {

    /**
     * Constructor for GetWarehouseByStreetAndCityAndPostalCodeQuery.
     * @param street the street address of the warehouse
     * @param city the city address of the warehouse
     * @param postalCode the postal code of the warehouse
     */
    public GetWarehouseByStreetAndCityAndPostalCodeQuery {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code cannot be null or blank");
        }
    }
}