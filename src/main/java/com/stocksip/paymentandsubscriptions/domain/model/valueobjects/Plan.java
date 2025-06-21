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
public record Plan(String type) {

    /**
     * This constructor validates the input parameter to ensure that it is not null or empty.
     *
     * @param type The type of the plan to be validated.
     * @throws IllegalArgumentException if the type parameter is null or empty.
     */
    public Plan {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Plan type cannot be null or empty");
        }
    }
}
