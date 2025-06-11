package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to retrieve all reports for the guide using guideName.
 * @param guideName - the guideName to retrieve all the data.
 * @since 1.0
 */
public record GetCareGuideByGuideNameQuery(String guideName) {
    public GetCareGuideByGuideNameQuery{
        if(guideName==null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null");
        }
    }
}
