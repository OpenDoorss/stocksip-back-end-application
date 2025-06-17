package com.stocksip.profilemanagement.domain.model.valueobjects;

public record UserName(String userName) {

    public UserName () { this(null); }

    public UserName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
        this.userName = userName;
    }
}
