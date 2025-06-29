package com.stocksip.inventorymanagement.domain.model.commands;

public record CreateCareGuideWithoutProductCommand(
    String guideName,
    String type,
    String description,
    String imageUrl,
    String accountId
) {
    public CreateCareGuideWithoutProductCommand {
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
    }
}