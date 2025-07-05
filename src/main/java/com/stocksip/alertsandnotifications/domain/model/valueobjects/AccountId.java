package com.stocksip.alertsandnotifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This record defines the identifier of a profile that will receive alerts and notifications.
 *
 * @summary
 * Value object that encapsulates the unique identifier for an account,
 * ensuring it is a valid non-empty string.
 *
 * @param accountId The unique identifier for the account.
 * @since 1.0
 */
@Embeddable
public record AccountId(String accountId) {
    /**
     * Validates the account ID parameter.
     */
    public AccountId {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID must be a non-empty string.");
        }
    }
} 