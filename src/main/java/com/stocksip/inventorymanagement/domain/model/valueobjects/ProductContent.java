package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Represents the content of a product in the inventory management system.
 * This value object encapsulates the content quantity and provides methods to manipulate it.
 *
 * @param content The content of the product in cubic meters, must be a positive number.
 */
@Embeddable
public record ProductContent(Double content) {

    /**
     * This constructor validates the input parameter to ensure that it is a positive number.
     *
     * @param content The content of the product in cubic meters.
     * @throws IllegalArgumentException if the content is not a positive number.
     */
    public ProductContent {
        if (!isContentValidate(content)) {
            throw new IllegalArgumentException("Content must be a positive number.");
        }
    }

    /**
     * Validates the content.
     *
     * @param content The content of the product in cubic meters.
     * @return true if the content is a positive number, false otherwise.
     */
    private static boolean isContentValidate(Double content) {
        return !(content.isNaN() || content <= 0);
    }

    /**
     * Updates the content of the product.
     *
     * @param content The new content of the product in cubic meters.
     * @return A new instance of ProductContent with the updated content.
     * @throws IllegalArgumentException if the content is not a positive number.
     */
    public ProductContent updateContent(Double content) {
        if (!isContentValidate(content)) {
            throw new IllegalArgumentException("Updated content must be a positive number.");
        }
        return new ProductContent(content);
    }
}
