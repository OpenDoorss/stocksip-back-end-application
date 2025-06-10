package com.stocksip.analyticsreporting.domain.services;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;

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
     * @throws IllegalArgumentException If productName, type, reportDate or lostAmount is null or empty
     * @see CreateReportCommand
     */
    Optional<Report> handle(CreateReportCommand command);
    Optional<Report> updateReport(Long id, CreateReportCommand command);
    boolean deleteReport(Long id);
}
