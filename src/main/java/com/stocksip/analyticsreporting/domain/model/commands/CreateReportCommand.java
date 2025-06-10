package com.stocksip.analyticsreporting.domain.model.commands;
import java.util.Date;

/**
 * CreateReportCommand
 *
 * @summary
 * CreateReportCommand is a record class that represents the command to create a report in the analytics reporting system.
 */
public record CreateReportCommand(
                                   String productName,
                                   String type,
                                   double price,
                                   double amount,
                                   Date reportDate,
                                  double lostAmount) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException If any of the required fields are null or empty
     */
    public CreateReportCommand {
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty");
        if (type == null || type.isEmpty())
            throw new IllegalArgumentException("Report type cannot be null or empty");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if (reportDate == null)
            throw new IllegalArgumentException("Report date cannot be null");
        if (lostAmount < 0)
            throw new IllegalArgumentException("Lost amount cannot be negative");
    }

}
