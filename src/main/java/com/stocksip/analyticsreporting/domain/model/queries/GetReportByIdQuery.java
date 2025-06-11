package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to get a report by its id.
 * @param id - the id of the report.
 * @since 1.0
 */
public record GetReportByIdQuery(Long id) {
    public GetReportByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}
