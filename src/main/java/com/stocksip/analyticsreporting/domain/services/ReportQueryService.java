package com.stocksip.analyticsreporting.domain.services;


import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.queries.GetReportByIdQuery;
import com.stocksip.analyticsreporting.domain.model.queries.GetReportByProductNameQuery;
import com.stocksip.analyticsreporting.domain.model.queries.GetReportByReportDateAndLostAmountQuery;
import com.stocksip.analyticsreporting.domain.model.queries.GetReportByTypeQuery;

import java.util.List;
import java.util.Optional;

/**
 * @name ReportQueryService
 *
 * @summary
 * This interface represents the service to handle report queries.
 * @since 1.0.0
 */
public interface ReportQueryService {
    /**
     * Retrieves a report by its ID.
     *
     * @param id the ID of the report to retrieve
     * @return an Optional containing the found report, or empty if not found
     */
    Optional<Report> getReportById(Long id);
    /**
     * Retrieves all reports.
     *
     * @return a list of all reports, or an empty list if none found
     */
    List<Report> getAllReports();
    /**
     * Handles a query to find reports by product name.
     *
     * @param query the query containing the product name
     * @return a list of matching reports, or an empty list if none found
     */
    List<Report>handle(GetReportByProductNameQuery query);
    /**
     * Handles a query to find a report by ID.
     *
     * @param query the query containing the report ID
     * @return an Optional containing the found report, or empty if not found
     */
    Optional<Report> handle(GetReportByIdQuery query);
    /**
     * Handles a query to find a report by report date and lost amount.
     *
     * @param query the query containing the report date and lost amount
     * @return an Optional containing the found report, or empty if not found
     */
    Optional<Report> handle(GetReportByReportDateAndLostAmountQuery query);
    /**
     * Handles a query to find a report by type.
     *
     * @param query the query containing the report type
     * @return an Optional containing the found report, or empty if not found
     */
    Optional<Report> handle(GetReportByTypeQuery query);
}
