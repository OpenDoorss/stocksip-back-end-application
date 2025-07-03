package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public enum OrderStatus {
    RECEIVED("Received"),
    IN_PROCESS("In Process"),
    ARRIVED("Arrived"),
    CANCELED("Canceled");

    private final String label;
    OrderStatus(String label) { this.label = label; }

    public String label() { return label; }

    @Override public String toString() { return label; }
}
