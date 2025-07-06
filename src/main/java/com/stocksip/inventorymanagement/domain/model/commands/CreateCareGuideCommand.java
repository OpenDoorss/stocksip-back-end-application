package com.stocksip.inventorymanagement.domain.model.commands;


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
public record CreateCareGuideCommand(
    String guideName,
    String type,
    String description,
    String imageUrl,  
    String accountId,
    Long productId
) {
    public CreateCareGuideCommand {
        if (guideName == null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null or empty");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }
}
