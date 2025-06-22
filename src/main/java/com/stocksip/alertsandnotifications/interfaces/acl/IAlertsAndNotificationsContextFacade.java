package com.stocksip.alertsandnotifications.interfaces.acl;

/**
 * Interface for the Alerts and Notifications context facade.
 * This interface defines the contract for the facade that provides
 * a clean interface for other bounded contexts to interact with
 * the Alerts and Notifications context.
 *
 * @summary
 * Facade interface that exposes methods for other bounded contexts
 * to create and manage alerts without exposing internal implementation details.
 *
 * @since 1.0
 */
public interface IAlertsAndNotificationsContextFacade {

    /**
     * Creates an alert with the specified parameters.
     * This method is used by other bounded contexts to create alerts.
     *
     * @param title      The title of the alert.
     * @param message    The message content of the alert.
     * @param severity   The severity level of the alert.
     * @param type       The type of the alert.
     * @param profileId  The ID of the profile associated with the alert.
     * @param productId  The ID of the product associated with the alert.
     * @param warehouseId The ID of the warehouse associated with the alert.
     * @return The ID of the created alert, or an empty string if the alert could not be created.
     */
    String createAlert(String title, String message, String severity, String type, String profileId,
                      String productId, String warehouseId);
} 