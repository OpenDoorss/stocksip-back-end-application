package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Optional;

/**
 * Represents the name of a product in the inventory management system.
 * This value object encapsulates the brand, liquor type, and an optional additional name of the product.
 *
 * @param brand The brand of the product, must not be null.
 * @param type The type of liquor, must not be null.
 * @param name An optional additional name for the product, can be empty but not blank if present.
 */
@Embeddable
public record ProductName(BrandName brand, LiquorType type, String name) {

    /**
     * This constructor validates the input parameters to ensure that brand and type are not null,
     * and if an additional name is provided, it must not be blank.
     *
     * @param brand The brand of the product.
     * @param type The type of liquor.
     * @param name An optional additional name for the product.
     * @throws IllegalArgumentException if brand or type is null, or if name is provided and is blank.
     */
    public ProductName {
        if (brand == null || type == null) {
            throw new IllegalArgumentException("Brand and type must not be null.");
        }
        if (name == "") {
            throw new IllegalArgumentException("Name must not be blank if provided.");
        }
    }

    /**
     * Method that returns the full name of the product,
     *
     * @return A string representing the full name of the product, which includes the brand, type, and additional name if present.
     */
    public String getFullName() {
        return brand.toString() + " " + type.toString() +
               (name.isBlank() ? "" : " " + name);
    }

    /**
     * Updates the additional name of the product.
     *
     * @param newName The new additional name to be set for the product.
     * @return A new instance of ProductName with the updated additional name.
     * @throws IllegalArgumentException if the new name is blank.
     */
    public ProductName updateAdditionalName(String newName) {
        if (newName != null && newName.isBlank()) {
            throw new IllegalArgumentException("Updated name must not be blank.");
        }
        return new ProductName(brand, type, newName);
    }
}
