package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Role(String value) {

    public Role {
        if (value == null ||
                !(value.equals("Supplier") || value.equals("Liquor Store Owner"))) {
            throw new IllegalArgumentException("Role must be 'Supplier' or 'Liquor Store Owner'");
        }
    }

    @Override public String toString() { return value; }
    public String value() { return value; }
}