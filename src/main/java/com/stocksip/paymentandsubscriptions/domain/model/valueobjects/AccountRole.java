package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum AccountRole {

    LIQUOR_STORE_OWNER("Liquor Store Owner"),
    SUPPLIER("Supplier");

    private final String displayName;

    AccountRole(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)   // ← ¡CLAVE!
    public static AccountRole fromJson(String value) {
        System.out.println(">>> fromJson: " + value);   // Debug: DEBE verse en consola
        if (value == null) return null;
        String normalized = normalize(value);
        for (AccountRole role : values()) {
            if (normalize(role.name()).equals(normalized) ||
                    normalize(role.displayName).equals(normalized)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Rol desconocido: " + value);
    }

    /* helpers */
    private static String normalize(String s) {
        return s.toUpperCase(Locale.ROOT).replaceAll("[ _]", "");
    }
}