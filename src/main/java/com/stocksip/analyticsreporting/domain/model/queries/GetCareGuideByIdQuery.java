package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to get a care guide by its id.
 * @param id - the id of the report.
 * @since 1.0
 */
public record GetCareGuideByIdQuery(Long id) {
    public GetCareGuideByIdQuery{
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}
