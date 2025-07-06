package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * @summary
 * This class represents the query to get a care guide by type and description.
 * @param type- The tyope of the care guide.
 * @param description - The description of the care guide.
 * @since 1.0
 */
public record GetCareGuideByTypeAndDescriptionQuery(String type, String description) {
    public GetCareGuideByTypeAndDescriptionQuery {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
    }

}
