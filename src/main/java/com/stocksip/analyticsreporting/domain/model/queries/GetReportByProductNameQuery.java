package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to retrieve all reports for the product using productName.
 * @param productName - the productName to retrieve all the data.
 */
public record GetReportByProductNameQuery(String productName) {
    public GetReportByProductNameQuery {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
    }
}
