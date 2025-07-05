package com.stocksip.alertsandnotifications.application.acl;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.commands.CreateAlertCommand;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.AccountId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId;
import com.stocksip.alertsandnotifications.domain.services.AlertCommandService;
import com.stocksip.alertsandnotifications.domain.services.AlertQueryService;
import com.stocksip.alertsandnotifications.interfaces.acl.IAlertsAndNotificationsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class serves as a facade for the Alerts and Notifications context, providing methods to create alerts for other contexts.
 *
 * @summary
 * Facade class that provides a clean interface for other bounded contexts to interact with
 * the Alerts and Notifications context without exposing internal implementation details.
 *
 * @since 1.0
 */
@Service
public class AlertsAndNotificationsContextFacade implements IAlertsAndNotificationsContextFacade {

    private final AlertCommandService alertCommandService;

    /**
     * Constructor for AlertsAndNotificationsContextFacade.
     *
     * @param alertCommandService The command service for handling alert operations.
     * @param alertQueryService   The query service for retrieving alert information.
     */
    public AlertsAndNotificationsContextFacade(
            AlertCommandService alertCommandService,
            AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
    }

    /**
     * This method creates an alert with the specified parameters.
     * It is used to receive information from other contexts and create an alert.
     *
     * @param title      The title of the alert.
     * @param message    The message content of the alert.
     * @param severity   The severity level of the alert.
     * @param type       The type of the alert.
     * @param accountId  The ID of the account associated with the alert.
     * @param productId  The ID of the product associated with the alert.
     * @param warehouseId The ID of the warehouse associated with the alert.
     * @return The ID of the created alert, or an empty string if the alert could not be created.
     */
    public String createAlert(String title, String message, String severity, String type, String accountId,
                             String productId, String warehouseId) {
        
        var targetProductId = new ProductId(productId);
        var targetWarehouseId = new WarehouseId(warehouseId);
        var targetAccountId = new AccountId(accountId);
        
        var createAlertCommand = new CreateAlertCommand(
                title,
                message,
                severity,
                type,
                targetAccountId,
                targetProductId,
                targetWarehouseId
        );
        
        Optional<Alert> alert = alertCommandService.handle(createAlertCommand);
        return alert.map(alertEntity -> alertEntity.AlertId.toString()).orElse("");
    }
} 