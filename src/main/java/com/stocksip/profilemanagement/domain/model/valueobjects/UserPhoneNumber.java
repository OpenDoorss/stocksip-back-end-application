package com.stocksip.profilemanagement.domain.model.valueobjects;

public record UserPhoneNumber(String userPhoneNumber) {

    public UserPhoneNumber() {
        this(null);
    }

    public UserPhoneNumber(String userPhoneNumber) {
        if (userPhoneNumber == null || userPhoneNumber.isBlank()) {
            throw new IllegalArgumentException("User phone number cannot be null or blank");
        }
        this.userPhoneNumber = userPhoneNumber;
    }
}
