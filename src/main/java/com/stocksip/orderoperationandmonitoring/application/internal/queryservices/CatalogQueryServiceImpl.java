package com.stocksip.orderoperationandmonitoring.application.internal.queryservices;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.queries.*;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogQueryService;
import com.stocksip.orderoperationandmonitoring.infrastructure.clients.AccountClient;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogItemRepository;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogRepository;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogQueryServiceImpl implements CatalogQueryService {

    private final CatalogRepository     catalogRepo;
    private final AccountRepository accountRepository;

    private final CatalogItemRepository itemRepo;
    private final AccountClient accountClient;

    /* ────────────────────────────────
     * CATALOGS
     * ──────────────────────────────── */

    public List<Catalog> getPublishedCatalogsByProviderEmail(String email) {

        var accountOpt = accountRepository.findByEmailAndRole(email, "Supplier");

        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Proveedor inexistente o rol inválido");
        }

        var supplier = accountOpt.get();

        return catalogRepo.findByAccountIdAndIsPublishedTrue(
                new AccountId(supplier.getAccountId()));
    }

    @Override
    public List<Catalog> handle(GetCatalogsByAccountQuery q) {
        return catalogRepo.findByAccountId(q.accountId());
    }

    @Override
    public List<Catalog> handle(GetPublishedCatalogsByAccountIdQuery q) {
        return catalogRepo.findByAccountIdAndIsPublishedTrue(q.accountId());
    }

    @Override
    public List<Catalog> handle(GetPublishedCatalogsQuery q) {
        return catalogRepo.findByIsPublishedTrue();
    }

    @Override
    public Optional<Catalog> handle(GetCatalogByIdQuery q) {
        return catalogRepo.findById(q.catalogId());
    }

    @Override
    public List<Catalog> handle(GetAllCatalogsQuery q) {
        return catalogRepo.findAll();
    }


    /* ────────────────────────────────
     * CATALOG ITEMS
     * ──────────────────────────────── */
    @Override
    public List<CatalogItem> handle(GetCatalogItemsByCatalogIdQuery q) {
        return itemRepo.findByCatalogCatalogId(q.catalogId());
    }

    @Override
    public Optional<CatalogItem> handle(GetCatalogItemByIdQuery q) {
        return itemRepo.findById(q.catalogItemId());
    }
}
