package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing a Catalog ID.
 *
 * @summary
 * Represents a unique identifier for a catalog.
 * This class is immutable and ensures that the catalog ID is always a positive number.
 * @param catalogId the unique identifier for the catalog that must be a positive number.
 *
 * @see IllegalArgumentException
 *
 * @since 1.0.0
 */
@Embeddable
public record CatalogId(Long catalogId) {

    public CatalogId {
        if (catalogId == null || catalogId <= 0) {
            throw new IllegalArgumentException("Catalog ID must be a positive number.");
        }
    }
}
