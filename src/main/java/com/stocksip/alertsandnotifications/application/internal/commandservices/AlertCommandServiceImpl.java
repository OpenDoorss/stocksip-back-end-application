package com.stocksip.alertsandnotifications.application.internal.commandservices;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.commands.CreateAlertCommand;
import com.stocksip.alertsandnotifications.domain.model.commands.MarkAlertAsReadCommand;
import com.stocksip.alertsandnotifications.domain.services.AlertCommandService;
import com.stocksip.alertsandnotifications.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This class implements the command service for handling alert-related commands.
 *
 * @summary
 * Implementation of the AlertCommandService interface that handles all command operations
 * related to alerts, including creation and status updates.
 *
 * @since 1.0
 */
@Service
@Transactional
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertJpaRepository alertRepository;

    /**
     * Constructor for AlertCommandServiceImpl.
     *
     * @param alertRepository The repository for managing alerts.
     */
    public AlertCommandServiceImpl(AlertJpaRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * This method handles the creation of a new alert based on the provided command.
     *
     * @param command The command containing the details for creating a new alert.
     * @return The created alert object, or empty if the creation fails.
     */
    @Override
    public Optional<Alert> handle(CreateAlertCommand command) {
        var alert = new Alert(command);
        var savedAlert = alertRepository.save(alert);
        return Optional.of(savedAlert);
    }

    /**
     * This method handles the marking of an alert as read based on the provided command.
     *
     * @param command The command containing the ID of the alert to be marked as resolved.
     * @return The updated alert object after marking it as resolved, or empty if the alert does not exist.
     * @throws IllegalArgumentException when the alert with the specified ID does not exist in the repository.
     */
    @Override
    public Optional<Alert> handle(MarkAlertAsReadCommand command) {
        var alertId = Long.parseLong(command.alertId());
        var alertToMark = alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Alert with ID " + command.alertId() + " does not exist."));
        
        alertToMark.read();
        var updatedAlert = alertRepository.save(alertToMark);
        return Optional.of(updatedAlert);
    }
} 