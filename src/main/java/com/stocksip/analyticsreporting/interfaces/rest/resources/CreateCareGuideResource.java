package com.stocksip.analyticsreporting.interfaces.rest.resources;

/**
 * @summary 
 * Resource class for creating CareGuide.
 * @param guideName - the guide name of the CareGuide.
 * @param type - the type of the CareGuide.
 * @param description - the description of the CareGuide.
 */
public record CreateCareGuideResource(String guideName,String type, String description) {
    /**
     * Validates the resource.
     *
     * @throws IllegalArgumentException If any of the required fields are null or empty
     */
    public CreateCareGuideResource {
        if (guideName==null||guideName.isEmpty()||type == null || type.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Type and description must not be null or empty");
        }
    }
}
