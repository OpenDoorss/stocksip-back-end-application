package com.stocksip.alertsandnotifications.domain.model.queries;

/**
 * This query is used to retrieve an alert by its unique identifier.
 *
 * @summary
 * Query object that encapsulates the information needed to retrieve
 * an alert by its unique identifier.
 *
 * @param alertId The unique identifier of the alert to be retrieved.
 * @since 1.0
 */
public record GetAlertByIdQuery(String alertId) {

    /**
     * Validates the query parameters.
     */
    public GetAlertByIdQuery {
        if (alertId == null || alertId.isBlank()) {
            throw new IllegalArgumentException("AlertId cannot be null or blank.");
        }
    }
} 