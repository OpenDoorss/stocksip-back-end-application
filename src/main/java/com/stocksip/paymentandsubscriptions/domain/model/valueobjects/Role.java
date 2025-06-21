package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This is a value object that represents a role in the payment and subscription domain.
 * It encapsulates the role as a string and ensures that it is not null or empty.
 *
 * @summary
 * The Role class is a record
 *
 * @since 1.0.0
 */
@Embeddable
public record Role(String role) {

    public Role {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
    }
}
