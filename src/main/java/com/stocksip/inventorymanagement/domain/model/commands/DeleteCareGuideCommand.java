package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * DeleteCareGuideCommand
 *
 * @summary
 * DeleteCareGuideCommand is a record class that represents the command to delete a care guide in the analytics reporting system.
 * @param id The unique identifier of the care guide to be deleted (required, non-negative)
 * @since 1.0
 */
public record DeleteCareGuideCommand(Long id) {
    public DeleteCareGuideCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid care guide ID");
        }
    }
}

