package com.stocksip.orderoperationandmonitoring.domain.services;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Facade that encapsulates the query operations for the catalog domain.
 * with {@link Catalog} and {@link CatalogItem}.
 *
 * @since 1.0.0
 */
public interface CatalogQueryService {
    List<Catalog> handle(GetCatalogsByAccountQuery query);

    List<Catalog> handle(GetPublishedCatalogsByAccountIdQuery query);

    List<Catalog> handle(GetPublishedCatalogsQuery query);

    List<Catalog> handle(GetAllCatalogsQuery query);

    Optional<Catalog> handle(GetCatalogByIdQuery query);

    List<CatalogItem> handle(GetCatalogItemsByCatalogIdQuery query);

    Optional<CatalogItem> handle(GetCatalogItemByIdQuery query);

    List<Catalog> getPublishedCatalogsByProviderEmail(String email);
}
