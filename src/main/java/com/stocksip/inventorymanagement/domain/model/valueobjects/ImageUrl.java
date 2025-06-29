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

    /**
     * Static factory method to create a default ImageUrl instance.
     * This method returns a predefined image URL that can be used as a default value.
     *
     * @return A new ImageUrl instance with the default image URL.
     */
    public static ImageUrl defaultImageUrl() {
        return new ImageUrl("https://res.cloudinary.com/deuy1pr9e/image/upload/v1749934502/warehouses/ed50b16b-91b8-4027-a23d-71aaa158bc8b.jpg");
    }
}
