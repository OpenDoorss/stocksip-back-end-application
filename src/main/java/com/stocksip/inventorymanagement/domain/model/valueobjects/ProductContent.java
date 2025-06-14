package com.stocksip.inventorymanagement.domain.model.valueobjects;

public record ProductContent(Double content) {

    /**
     * This constructor validates the input parameter to ensure that it is a positive number.
     *
     * @param content The content of the product in cubic meters.
     * @throws IllegalArgumentException if the content is not a positive number.
     */
    public ProductContent {
        validateContent(content);
    }

    /**
     * Validates the content.
     *
     * @param content The content of the product in cubic meters.
     * @throws IllegalArgumentException if the content is not a positive number.
     */
    private static void validateContent(Double content) {
        if (content == null || content <= 0) {
            throw new IllegalArgumentException("Content must be a positive number.");
        }
    }
}
