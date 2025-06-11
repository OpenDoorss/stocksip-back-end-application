package com.stocksip.analyticsreporting.domain.services;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
import com.stocksip.analyticsreporting.domain.model.commands.DeleteReportCommand;
import com.stocksip.analyticsreporting.domain.model.commands.UpdateReportCommand;

import java.util.Optional;

/**
 * @name ReportCommandService
 * @summary
 * This interface represents the service to handle report commands.
 */
public interface ReportCommandService {
    /**
     * Handles the create report command.
     * @param command The create report command.
     * @return The created report.
     *
     * @throws IllegalArgumentException If productId, type, price, amount, reportDate or lostAmount is null or empty
     * @see CreateReportCommand
     */
    Optional<Report> handle(CreateReportCommand command);
    /**
     * Handles the update report command.
     * @param command The update report command.
     * @return The updated report.
     *
     * @throws IllegalArgumentException If id, productId, type, price, amount, reportDate or lostAmount is null
     * @see UpdateReportCommand
     */
    Optional<Report> handle(UpdateReportCommand command);
    /**
     * Handles the delete report command.
     * @param command The delete report command.
     *
     * @throws IllegalArgumentException If id is null or less than or equal to 0
     * @see DeleteReportCommand
     */
    void handle(DeleteReportCommand command);
}
