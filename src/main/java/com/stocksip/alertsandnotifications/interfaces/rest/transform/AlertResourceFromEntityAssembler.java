package com.stocksip.alertsandnotifications.interfaces.rest.transform;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.interfaces.rest.resources.AlertResource;

/**
 * This class is responsible for transforming an Alert entity into an AlertResource.
 *
 * @summary
 * Static utility class that provides methods to transform Alert domain entities
 * into REST API resources for external consumption.
 *
 * @since 1.0
 */
public final class AlertResourceFromEntityAssembler {

    // Private constructor to prevent instantiation
    private AlertResourceFromEntityAssembler() {}

    /**
     * Method to transform an Alert entity into an AlertResource.
     *
     * @param entity The Alert entity to be transformed into an AlertResource.
     * @return The AlertResource that contains the details of the alert.
     */
    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(
                entity.AlertId.toString(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getSeverity().name(),
                entity.getType().name(),
                entity.getProductId().productId(),
                entity.getWarehouseId().warehouseId(),
                entity.getState().name());
    }
} 