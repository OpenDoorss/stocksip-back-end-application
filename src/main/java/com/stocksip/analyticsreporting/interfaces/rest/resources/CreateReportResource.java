package com.stocksip.analyticsreporting.interfaces.rest.resources;

import java.util.Date;

public record CreateReportResource(String productName, String type,double price,double amount, Date reportDate, double lostAmount) {
    public CreateReportResource{
        if (productName == null || productName.isEmpty() || type == null || type.isEmpty() || reportDate == null || lostAmount < 0) {
            throw new IllegalArgumentException("Product name and type must not be null or empty");
        }
        if (price < 0 || amount < 0 ) {
            throw new IllegalArgumentException("Price, amount and lost amount must not be negative");
        }
    }
}
