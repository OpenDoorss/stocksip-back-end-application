package com.stocksip.orderoperationandmonitoring.application.internal.queryservices;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.queries.*;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogQueryService;
import com.stocksip.orderoperationandmonitoring.infrastructure.clients.AccountClient;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogItemRepository;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogQueryServiceImpl implements CatalogQueryService {

    private final CatalogRepository     catalogRepo;
    private final CatalogItemRepository itemRepo;
    private final AccountClient accountClient;

    /* ────────────────────────────────
     * CATALOGS
     * ──────────────────────────────── */

    public List<Catalog> getPublishedCatalogsByProviderEmail(String email) {
        var accountOpt = accountClient.getAccountByEmail(email);
        if (accountOpt.isEmpty()) {
            System.out.println("No se encontró cuenta para el email: " + email);
            throw new IllegalArgumentException("No se encontró cuenta");
        }

        var account = accountOpt.get();
        System.out.println("Cuenta encontrada: " + account.getAccountId() + ", rol: " + account.getRole());

        if (!"Supplier".equals(account.getRole())) {
            System.out.println("El rol no es Supplier");
            throw new IllegalArgumentException("No es proveedor");
        }

        Long supplierId = account.getAccountId();
        return catalogRepo.findByAccountIdAndIsPublishedTrue(new AccountId(supplierId));
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
