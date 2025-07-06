package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public record ContentVolume(int value) {

    public ContentVolume {
        if (value <= 0) throw new IllegalArgumentException("Volume must be positive");
    }
}