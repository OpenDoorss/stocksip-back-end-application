package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Optional;

/**
 * Represents the name of a product in the inventory management system.
 * This value object encapsulates the brand, liquor type, and an optional additional name of the product.
 *
 * @param name An optional additional name for the product, can be empty but not blank if present.
 */
@Embeddable
public record ProductName(String name) {

    /**
     * This constructor validates the input parameters to ensure that brand and type are not null,
     * and if an additional name is provided, it must not be blank.
     *
     * @param name An optional additional name for the product.
     * @throws IllegalArgumentException if brand or type is null, or if name is provided and is blank.
     */
    public ProductName {
        if (name == "") {
            throw new IllegalArgumentException("Name must not be blank if provided.");
        }
    }

}
