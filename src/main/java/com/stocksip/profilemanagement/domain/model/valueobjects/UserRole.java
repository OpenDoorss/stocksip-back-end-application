package com.stocksip.profilemanagement.domain.model.valueobjects;

import java.io.Serializable;

public record UserRole(String role) implements Serializable {
    public UserRole() {
        this(null);
    }

    public UserRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("User role cannot be null or blank");
        }
        this.role = role;
    }
}
