package com.stocksip.analyticsreporting.domain.model.commands;

import java.util.Date;

/**
 * UpdateReportCommand
 *
 * @summary
 * UpdateReportCommand is a record class that represents the command to update a report in the analytics reporting system.
 * @param id The unique identifier of the report to be updated 
 * @param productId The ID of the product associated with the report 
 * @param type The type of the report 
 * @param price The price of the product in the report 
 * @param amount The quantity of the product in the report 
 * @param reportDate The date of the report 
 * @param lostAmount The amount of product lost 
 * @since 1.0
 */
public record UpdateReportCommand(Long id, String productId, String type, double price, double amount, Date reportDate, double lostAmount) {
    /**
     * Constructs an UpdateReportCommand with the specified parameters.
     *
     * @param id        The unique identifier of the report.
     * @param productId The ID of the product associated with the report.
     * @param type      The type of the report.
     * @param price     The price of the product in the report.
     */
    public UpdateReportCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank.");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (reportDate == null) {
            throw new IllegalArgumentException("Report date cannot be null.");
        }
        if (lostAmount < 0) {
            throw new IllegalArgumentException("Lost amount cannot be negative.");
        }
    }
}
