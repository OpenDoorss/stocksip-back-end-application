package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

public record GeneralEmail(String value) {
    public GeneralEmail {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (!value.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static GeneralEmail from(String email) {
        return new GeneralEmail(email);
    }
}
