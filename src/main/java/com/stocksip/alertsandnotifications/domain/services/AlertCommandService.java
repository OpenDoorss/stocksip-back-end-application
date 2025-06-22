package com.stocksip.alertsandnotifications.domain.services;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.commands.CreateAlertCommand;
import com.stocksip.alertsandnotifications.domain.model.commands.MarkAlertAsReadCommand;

import java.util.Optional;

/**
 * Interface for the Alert Command Service.
 *
 * @summary
 * This interface defines the contract for handling alert-related commands,
 * including creation and status updates of alerts.
 *
 * @since 1.0
 */
public interface AlertCommandService {

    /**
     * Handles the creation of a new alert based on the provided command.
     *
     * @param command The command containing the details for creating a new alert.
     * @return The created alert object, or empty if the creation fails.
     */
    Optional<Alert> handle(CreateAlertCommand command);

    /**
     * Handles the marking of an alert as read based on the provided command.
     *
     * @param command The command containing the ID of the alert to be marked as resolved.
     * @return The updated alert object after marking it as resolved, or empty if the alert does not exist.
     */
    Optional<Alert> handle(MarkAlertAsReadCommand command);
} 