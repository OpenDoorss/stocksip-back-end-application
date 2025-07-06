package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This is a value object that represents the URL of an image.
 * @param imageUrl The URL of the image.
 *
 * @summary
 * The ImageUrl class is an embeddable value object that encapsulates the URL of an image.
 *
 * @since 1.0.0
 */
@Embeddable
public record ImageUrl(String imageUrl) {

    /**
     * Constructor for ImageUrl.
     * Validates that the image URL is not null or empty.
     *
     * @param imageUrl The URL of the image.
     * @throws IllegalArgumentException if the image URL is null or empty.
     */
    public ImageUrl {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty.");
        }
    }
}
