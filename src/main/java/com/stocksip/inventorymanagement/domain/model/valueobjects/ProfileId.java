package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing a Profile ID.
 *
 * @summary
 * Represents a unique identifier for a profile.
 * This class is immutable and ensures that the profile ID is always a positive number.
 * @param profileId the unique identifier for the profile that must be a positive number.
 *
 * @see IllegalArgumentException
 *
 * @since 1.0.0
 */
@Embeddable
public record ProfileId(Long profileId) {

    /**
     * Constructs a ProfileId with the given ID.
     *
     * @param profileId the unique identifier for the profile. Must be a positive number.
     * @throws IllegalArgumentException if the profileId is null or not positive.
     */
    public ProfileId {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("Profile ID must be a positive number.");
        }
    }

    /**
     * Factory method to create a ProfileId from a Long value.
     *
     * @param id the unique identifier for the profile. Must be a positive number.
     * @return a new ProfileId instance.
     * @throws IllegalArgumentException if the id is null or not positive.
     */
    public static ProfileId from(Long id) {
        return new ProfileId(id);
    }
}
