package com.stocksip.analyticsreporting.domain.model.commands;

/**
 * DeleteReportCommand
 *
 * @summary
 * DeleteReportCommand is a record class that represents the command to delete a report in the analytics reporting system.
 * @param id The unique identifier of the report to be deleted (required, non-negative)
 * @since 1.0
 */
public record DeleteReportCommand(Long id) {
    public DeleteReportCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid report ID");
        }
    }
}
