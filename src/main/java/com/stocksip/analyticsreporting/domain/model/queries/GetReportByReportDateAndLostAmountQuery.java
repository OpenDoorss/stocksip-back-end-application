package com.stocksip.analyticsreporting.domain.model.queries;

import java.util.Date;

/**
 * @summary
 * This class represents the query to get a report by reportDate and lostAmount.
 * @param reportDate - The reportDate of the report.
 * @param lostAmount - The lostAmount of the report.
 * @since 1.0
 */
public record GetReportByReportDateAndLostAmountQuery(Date reportDate, double lostAmount) {
    public GetReportByReportDateAndLostAmountQuery {
        if (reportDate == null) {
            throw new IllegalArgumentException("reportDate cannot be null");
        }
        if (lostAmount < 0) {
            throw new IllegalArgumentException("lostAmount cannot be negative");
        }
    }
}
