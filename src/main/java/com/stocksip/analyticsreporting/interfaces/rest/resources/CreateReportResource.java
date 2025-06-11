package com.stocksip.analyticsreporting.interfaces.rest.resources;

import java.util.Date;

/**
 * @summary 
 * Resource class for creating Report.
 * @param productName - the product name of the Report.
 * @param type - the type of the Report.
 * @param price - the price of the Report.
 * @param amount - the amount of the Report.
 * @param reportDate - the report date of the Report.
 * @param lostAmount - the lost amount of the Report.
 */
public record CreateReportResource(String productName, String type,double price,double amount, Date reportDate, double lostAmount) {
    /**
     * Validates the resource.
     *
     * @throws IllegalArgumentException If any of the required fields are null or empty
     */
    public CreateReportResource{
        if (productName == null || productName.isEmpty() || type == null || type.isEmpty() || reportDate == null || lostAmount < 0) {
            throw new IllegalArgumentException("Product name and type must not be null or empty");
        }
        if (price < 0 || amount < 0 ) {
            throw new IllegalArgumentException("Price, amount and lost amount must not be negative");
        }
    }
}
