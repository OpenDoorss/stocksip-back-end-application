package com.stocksip.alertsandnotifications.domain.model.commands;

import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProfileId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId;

/**
 * This command represents the creation of a new alert in the system.
 *
 * @summary
 * Command object that encapsulates all the necessary information to create
 * a new alert in the system.
 *
 * @param title      The title of the alert, providing a brief description of the alert.
 * @param message    The message of the alert, providing detailed information about the alert.
 * @param severity   The severity of the alert, indicating its importance or urgency.
 * @param type       The type of the alert, categorizing it into a specific type.
 * @param profileId  The unique identifier of the profile associated with the alert.
 * @param productId  The unique identifier of the product associated with the alert.
 * @param warehouseId The unique identifier of the warehouse associated with the alert.
 * @since 1.0
 */
public record CreateAlertCommand(
        String title,
        String message,
        String severity,
        String type,
        ProfileId profileId,
        ProductId productId,
        WarehouseId warehouseId) {

    /**
     * Validates the command parameters.
     */
    public CreateAlertCommand {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank.");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank.");
        }
        if (severity == null || severity.isBlank()) {
            throw new IllegalArgumentException("Severity cannot be null or blank.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank.");
        }
        if (profileId == null) {
            throw new IllegalArgumentException("ProfileId cannot be null.");
        }
        if (productId == null) {
            throw new IllegalArgumentException("ProductId cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("WarehouseId cannot be null.");
        }
    }
} 