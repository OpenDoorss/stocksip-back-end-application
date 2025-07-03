package com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    List<Catalog> findByAccountId(AccountId accountId);

    List<Catalog> findByAccountIdAndIsPublishedTrue(AccountId accountId);

    List<Catalog> findByIsPublishedTrue();
}