package com.stocksip.inventorymanagement.domain.model.commands;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProviderId;

import java.util.Date;
import java.util.Optional;

/**
 * CreateProductCommand
 *
 * @summary
 * CreateProductCommand is a record class that represents the command to register a product in a warehouse.
 */
public record CreateProductCommand(Optional<String> additionalName,
                                   String liquorType,
                                   String brandName,
                                   Date expirationDate,
                                   double unitPrice,
                                   int currentStock,
                                   int minimumStock,
                                   Long warehouseId,
                                   Optional<ProviderId> providerId) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If warehouse ID is null
     */
    public CreateProductCommand {
        if (warehouseId == null)
            throw new IllegalArgumentException("Warehouse ID cannot be null or empty");
    }
}
