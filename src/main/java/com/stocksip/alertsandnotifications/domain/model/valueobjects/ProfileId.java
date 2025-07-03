package com.stocksip.alertsandnotifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This record defines the identifier of a profile that will receive alerts and notifications.
 *
 * @summary
 * Value object that encapsulates the unique identifier for a profile,
 * ensuring it is a valid non-empty string.
 *
 * @param profileId The unique identifier for the profile.
 * @since 1.0
 */
@Embeddable
public record ProfileId(String profileId) {

    /**
     * Validates the profile ID parameter.
     */
    public ProfileId {
        if (profileId == null || profileId.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile ID must be a non-empty string.");
        }
    }
} 