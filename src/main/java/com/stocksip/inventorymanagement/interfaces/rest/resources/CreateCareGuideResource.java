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
        if (guideName == null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}

