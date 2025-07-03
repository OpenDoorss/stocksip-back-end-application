package com.stocksip.orderoperationandmonitoring.domain.services;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.*;

import java.util.Optional;

/**
 * Interface for handling commands related to Catalog operations.
 *
 * @summary
 * Handles catalog creation, updates, publication, and item management.
 *
 * @since 1.0.0
 */
public interface CatalogCommandService {

    Optional<Catalog> handle(CreateCatalogCommand command);
    Optional<Catalog> handle(Long catalogId, UpdateCatalogCommand command);
    Optional<Catalog> handle(PublishCatalogCommand command);

    Optional<CatalogItem> handle(CreateCatalogItemCommand command);
    boolean handle(DeleteCatalogItemCommand command);

    Optional<Catalog> handle(UpdateCatalogCommand command);
}
