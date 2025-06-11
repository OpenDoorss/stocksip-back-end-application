package com.stocksip.analyticsreporting.domain.model.commands;

/**
 * UpdateCareGuideCommand
 *
 * @summary
 * UpdateCareGuideCommand is a record class that represents the command to update a care guide in the analytics reporting system.
 * @param id The unique identifier of the care guide to be updated (required, non-negative)
 * @param guideName The name or title of the care guide (required, non-empty)
 * @param type The category or classification of the guide (required, non-empty)
 * @param description The detailed content of the care guide (required, non-empty)
 * @since 1.0
 */
public record UpdateCareGuideCommand(Long id, String guideName, String type, String description) {

    public UpdateCareGuideCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
        if (guideName == null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null or blank.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }
    }
}
