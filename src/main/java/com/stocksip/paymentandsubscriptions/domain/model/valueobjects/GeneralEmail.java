package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.ToString;

/**
 * This is a value object that represents a role in the payment and subscription domain.
 * It encapsulates the role as a string and ensures that it is not null or empty.
 *
 * @summary
 * The Role class is a record that encapsulates a role in the payment and subscription domain.
 *
 * @since 1.0.0
*/
@Embeddable
public record GeneralEmail(String email) {

    /**
     * This constructor validates the input parameter to ensure that it is not null or empty.
     *
     * @param email The email address to be validated.
     * @throws IllegalArgumentException if the email parameter is null or empty.
     */
    public GeneralEmail {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    public static GeneralEmail from(String email) {
        return new GeneralEmail(email);
    }
}
