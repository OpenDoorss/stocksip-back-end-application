package com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, String> {

    List<CatalogItem> findByCatalogCatalogId(Long catalogId);
}
