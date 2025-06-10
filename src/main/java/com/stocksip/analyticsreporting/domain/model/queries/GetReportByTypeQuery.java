package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to get a report by its type.
 * @param type - the type of the report.
 */
public record GetReportByTypeQuery(String type) {
    public GetReportByTypeQuery {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
    }
}
