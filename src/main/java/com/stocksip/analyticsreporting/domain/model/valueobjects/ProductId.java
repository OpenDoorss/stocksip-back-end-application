package com.stocksip.analyticsreporting.domain.model.valueobjects;

/**
 * @summary
 * This class represents the product ID value object.
 * @param productId - the product ID.
 * @since 1.0
 */
public record ProductId(Long productId) {
    public ProductId{
        if (productId==null||productId <= 0)
            throw new IllegalArgumentException("productId is null or empty");
    }
}
