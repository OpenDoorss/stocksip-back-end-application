package com.stocksip.inventorymanagement.application.acl;

import com.stocksip.alertsandnotifications.interfaces.acl.IAlertsAndNotificationsContextFacade;
import org.springframework.stereotype.Service;

/**
 * This service is used to create alerts and notifications in the Alerts and Notifications context.
 * The information will be sent from the Inventory Management context to the Alerts and Notifications context.
 *
 * @summary
 * Service that provides methods for the Inventory Management context to interact with
 * the Alerts and Notifications context through the facade pattern.
 *
 * @since 1.0
 */
@Service
public class ExternalAlertsAndNotificationsService {

    private final IAlertsAndNotificationsContextFacade alertsAndNotificationsContextFacade;

    /**
     * Constructor for ExternalAlertsAndNotificationsService.
     *
     * @param alertsAndNotificationsContextFacade The facade for the Alerts and Notifications context.
     */
    public ExternalAlertsAndNotificationsService(IAlertsAndNotificationsContextFacade alertsAndNotificationsContextFacade) {
        this.alertsAndNotificationsContextFacade = alertsAndNotificationsContextFacade;
    }

    /**
     * The method is used to create an alert in the Alerts and Notifications context.
     *
     * @param title      The title of the alert.
     * @param message    The message content of the alert.
     * @param severity   The severity level of the alert.
     * @param type       The type of the alert.
     * @param profileId  The ID of the profile associated with the alert.
     * @param productId  The ID of the product associated with the alert.
     * @param warehouseId The ID of the warehouse associated with the alert.
     */
    public void createAlert(String title, String message, String severity, String type, String profileId, String productId,
                           String warehouseId) {
        var alertCreated = alertsAndNotificationsContextFacade.createAlert(title, 
            message, severity, type, profileId, productId, warehouseId);
    }
} 