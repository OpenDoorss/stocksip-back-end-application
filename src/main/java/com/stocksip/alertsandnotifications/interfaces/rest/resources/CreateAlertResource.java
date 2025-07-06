package com.stocksip.alertsandnotifications.interfaces.rest.resources;

import com.stocksip.alertsandnotifications.domain.model.valueobjects.AccountId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId;

/**
 * This record defines the resource for creating a new alert.
 *
 * @summary
 * Resource object that represents the data needed to create a new alert,
 * containing all the required information for alert creation.
 *
 * @param title      The title of the alert.
 * @param message    The message content of the alert.
 * @param severity   The severity level of the alert.
 * @param type       The type of the alert.
 * @param accountId  El identificador único de la cuenta asociada a la alerta.
 * @param productId  The unique identifier of the product associated with the alert.
 * @param warehouseId The unique identifier of the warehouse associated with the alert.
 * @since 1.0
 */
public record CreateAlertResource(
        String title,
        String message,
        String severity,
        String type,
        AccountId accountId,
        ProductId productId,
        WarehouseId warehouseId) {
} 