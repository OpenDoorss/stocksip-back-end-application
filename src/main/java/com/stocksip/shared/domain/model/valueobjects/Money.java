package com.stocksip.shared.domain.model.valueobjects;

import lombok.Getter;

@Getter
public class Money {
    private final double amount;
    private final Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency.getCode());
    }
}
