package com.stocksip.alertsandnotifications.interfaces.rest.transform;

import com.stocksip.alertsandnotifications.domain.model.commands.MarkAlertAsReadCommand;

/**
 * This static class is responsible for transforming a string alertId into a MarkAlertAsReadCommand.
 *
 * @summary
 * Static utility class that provides methods to transform alert IDs
 * into MarkAlertAsReadCommand objects for use in the application layer.
 *
 * @since 1.0
 */
public final class MarkAlertAsReadCommandFromResourceAssembler {

    // Private constructor to prevent instantiation
    private MarkAlertAsReadCommandFromResourceAssembler() {}

    /**
     * This method transforms a string alertId into a MarkAlertAsReadCommand.
     *
     * @param alertId The unique identifier of the alert to be marked as read.
     * @return The command that can be used to mark the alert as read.
     */
    public static MarkAlertAsReadCommand toCommandFromResource(String alertId) {
        return new MarkAlertAsReadCommand(alertId);
    }
} 