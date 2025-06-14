package com.stocksip.shared.domain.model.valueobjects;

import lombok.Getter;

@Getter
public class Currency {
    private final String code;

    public Currency(String code) {
        this.code = code;
    }
}
