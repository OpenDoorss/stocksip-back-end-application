package com.stocksip.alertsandnotifications.domain.model.queries;

import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProfileId;

/**
 * This query is used to retrieve all alerts associated with a specific profile.
 *
 * @summary
 * Query object that encapsulates the information needed to retrieve
 * all alerts associated with a specific profile.
 *
 * @param profileId The unique identifier of the profile for which alerts are being requested.
 * @since 1.0
 */
public record GetAllAlertsByProfileIdQuery(ProfileId profileId) {

    /**
     * Validates the query parameters.
     */
    public GetAllAlertsByProfileIdQuery {
        if (profileId == null) {
            throw new IllegalArgumentException("ProfileId cannot be null.");
        }
    }
} 