package com.stocksip.analyticsreporting.domain.model.valueobjects;

/**
 * @summary
 * This class represents the image URL value object.
 * @param imageUrl - the image URL.
 * @since 1.0
 */
public record ImageUrl(String imageUrl) {
    public ImageUrl{
        if (imageUrl==null||imageUrl.isEmpty())
            throw new IllegalArgumentException("imageUrl is null or empty");
        if (!imageUrl.startsWith("https://res.cloudinary.com/"))
            throw new IllegalArgumentException("imageUrl must start with https://res.cloudinary.com/"); 
    }
    public static ImageUrl ImageUrlForDefault(){
        return new ImageUrl("https://res.cloudinary.com/deuy1pr9e/image/upload/v1747454213/g24tiltaf9nughb8km93.avif");
    }
}
