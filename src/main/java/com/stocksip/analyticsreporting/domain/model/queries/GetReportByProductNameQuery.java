package com.stocksip.analyticsreporting.domain.model.queries;

/**
 * @summary
 * This class represents the query to retrieve all reports for a product using product ID.
 * @param productId - the product ID as a string to retrieve all the data.
 * @since 1.0
 */
public record GetReportByProductNameQuery(String productId) {
    public GetReportByProductNameQuery {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
        try {
            Long.parseLong(productId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product ID must be a valid number");
        }
    }
    
    /**
     * @return the product ID as a Long
     */
    public Long getProductIdAsLong() {
        return Long.parseLong(productId);
    }
}
