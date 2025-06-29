package com.stocksip.inventorymanagement.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

/**
 * @summary
 * Resource class for creating CareGuide.
 * @param guideName - the guide name of the CareGuide.
 * @param type - the type of the CareGuide.
 * @param description - the description of the CareGuide.
 * @param image - the image of the CareGuide.
 */
public record CreateCareGuideResource(String guideName,String type, String description, MultipartFile image) {
    /**
     * Validates the resource.
     *
     * @throws IllegalArgumentException If any of the required fields are null or empty
     */
    public CreateCareGuideResource {
        if (guideName == null || guideName.isEmpty() ||
                type == null || type.isEmpty() ||
                description == null || description.isEmpty()) {
            throw new IllegalArgumentException("All fields must be provided");
        }
    }
}

