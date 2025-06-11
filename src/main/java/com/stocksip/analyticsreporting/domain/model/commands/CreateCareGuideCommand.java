package com.stocksip.analyticsreporting.domain.model.commands;

/**
 * CreateCareGuideCommand
 *
 * @summary
 * CreateCareGuideCommand is a record class that represents the command to create a cara guide in the analytics reporting system.
 * @param guideName The name or title of the care guide (required, non-empty)
 * @param type The category or classification of the guide (required, non-empty)
 * @param description The detailed content of the care guide (required, non-empty)
 * @since 1.0
 */
public record CreateCareGuideCommand(String guideName, String type, String description) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If any of the required fields are null or empty
     */
    public CreateCareGuideCommand {
        if (guideName == null || guideName.isEmpty())
            throw new IllegalArgumentException("Guide name cannot be null or empty");
        if (type == null || type.isEmpty())
            throw new IllegalArgumentException("Type cannot be null or empty");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Description cannot be null or empty");
    }

}
