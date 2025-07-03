package com.stocksip.alertsandnotifications.domain.model.commands;

/**
 * This command represents a request to mark an alert as resolved.
 *
 * @summary
 * Command object that encapsulates the information needed to mark
 * an alert as read in the system.
 *
 * @param alertId The unique identifier of the alert to be marked as resolved.
 * @since 1.0
 */
public record MarkAlertAsReadCommand(String alertId) {

    /**
     * Validates the command parameters.
     */
    public MarkAlertAsReadCommand {
        if (alertId == null || alertId.isBlank()) {
            throw new IllegalArgumentException("AlertId cannot be null or blank.");
        }
    }
} 