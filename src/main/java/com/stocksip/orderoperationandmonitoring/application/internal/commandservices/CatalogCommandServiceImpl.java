package com.stocksip.orderoperationandmonitoring.application.internal.commandservices;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.*;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.*;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogCommandService;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogItemRepository;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.CatalogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CatalogCommandServiceImpl implements CatalogCommandService {

    private final CatalogRepository catalogRepository;
    private final CatalogItemRepository catalogItemRepository;

    @Override
    @Transactional
    public Optional<Catalog> handle(CreateCatalogCommand command) {
        Catalog catalog = new Catalog(command);
        return Optional.of(catalogRepository.save(catalog));
    }

    @Override
    @Transactional
    public Optional<Catalog> handle(Long catalogId, UpdateCatalogCommand command) {
        Optional<Catalog> optionalCatalog = catalogRepository.findById(catalogId);
        if (optionalCatalog.isEmpty()) return Optional.empty();

        Catalog catalog = optionalCatalog.get();
        catalog.update(command);
        return Optional.of(catalogRepository.save(catalog));
    }

    @Override
    public Optional<Catalog> handle(PublishCatalogCommand cmd) {
        return catalogRepository.findById(cmd.catalogId())
                .map(c -> {
                    c.publish();
                    return catalogRepository.save(c);
                });
    }

    @Override
    @Transactional
    public Optional<CatalogItem> handle(CreateCatalogItemCommand command) {
        Optional<Catalog> optionalCatalog = catalogRepository.findById(command.catalogId());
        if (optionalCatalog.isEmpty()) return Optional.empty();

        Catalog catalog = optionalCatalog.get();
        CatalogItem item = new CatalogItem(command);
        catalog.addItem(item);
        catalogRepository.save(catalog);
        return Optional.of(item);
    }

    @Override
    @Transactional
    public boolean handle(DeleteCatalogItemCommand command) {
        Optional<CatalogItem> optionalItem = catalogItemRepository.findById(command.catalogItemId());
        if (optionalItem.isEmpty()) return false;

        CatalogItem item = optionalItem.get();
        Catalog catalog = item.getCatalog();
        if (catalog == null) return false;

        catalog.removeItem(item);
        catalogRepository.save(catalog);
        return true;
    }

    @Override @Transactional
    public Optional<Catalog> handle(UpdateCatalogCommand cmd) {
        return catalogRepository.findById(cmd.catalogId())
                .map(c -> {
                    c.update(cmd);
                    return catalogRepository.save(c);
                });
    }
}