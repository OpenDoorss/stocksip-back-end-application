package com.stocksip.alertsandnotifications.interfaces.rest.resources;

/**
 * This record defines the alert resource.
 *
 * @summary
 * Resource object that represents an alert for REST API responses,
 * containing all the necessary information to display alert details.
 *
 * @param id        The unique identifier of the alert.
 * @param title     The title of the alert.
 * @param message   The message content of the alert.
 * @param severity  The severity level of the alert.
 * @param type      The type of the alert.
 * @param productId The unique identifier of the product associated with the alert.
 * @param warehouseId The unique identifier of the warehouse associated with the alert.
 * @param state     The state of the alert.
 * @since 1.0
 */
public record AlertResource(
        String id,
        String title,
        String message,
        String severity,
        String type,
        String productId,
        String warehouseId,
        String state) {
} 