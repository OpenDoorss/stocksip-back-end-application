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


    List<Report> getAllReports();
    List<Report>handle(GetReportByProductNameQuery query);
    Optional<Report> handle(GetReportByIdQuery query);
    Optional<Report> handle(GetReportByReportDateAndLostAmountQuery query);
    Optional<Report> handle(GetReportByTypeQuery query);
}
