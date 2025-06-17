package com.stocksip.profilemanagement.domain.model.valueobjects;

import jakarta.validation.constraints.Email;

public record UserEmail(@Email String userEmail) {

    public UserEmail {
        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("User email cannot be null or blank");
        }
    }

    @Override
    public String toString() {
        return userEmail;
    }
}
