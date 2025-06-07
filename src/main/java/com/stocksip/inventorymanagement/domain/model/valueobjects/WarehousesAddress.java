package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * This is a value object that represents the address of a warehouse.
 * @param street The street address of the warehouse.
 * @param city The city where the warehouse is located.
 * @param state The state where the warehouse is located.
 * @param postalCode The postal code of the warehouse's address.
 * @param country The country where the warehouse is located.
 *
 * @summary
 * The WarehousesAddress class is an embeddable value object that encapsulates the address details of a warehouse.
 *
 * @since 1.0.0
 */
@Embeddable
public record WarehousesAddress(String street,
                                String city,
                                String state,
                                String postalCode,
                                String country) {

    /**
     * This constructor validates the input parameters to ensure that none of them are null or empty.
     *
     * @param street The street address of the warehouse.
     * @param city The city where the warehouse is located.
     * @param state The state where the warehouse is located.
     * @param postalCode The postal code of the warehouse's address.
     * @param country The country where the warehouse is located.
     *
     * @throws IllegalArgumentException if any of the parameters are null or empty.
     */
    public WarehousesAddress {

        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }

        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }

        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }

        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    /**
     * Returns the full address as a formatted string.
     * @return A string representation of the full address in the format "street, city, state, postalCode, country".
     */
    public String getFullAddress() {
        return String.format("%s, %s, %s, %s, %s", street, city, state, postalCode, country);
    }
}
