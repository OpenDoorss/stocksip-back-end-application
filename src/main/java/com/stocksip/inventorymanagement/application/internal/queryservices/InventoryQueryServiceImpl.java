package com.stocksip.inventorymanagement.application.internal.queryservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllProductsByWarehouseIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdQuery;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.stocksip.inventorymanagement.domain.services.InventoryQueryService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.InventoryRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the InventoryQueryService interface to provide methods for querying inventory data.
 *
 * @summary
 * Implementation of the InventoryQueryService interface for handling queries related to inventories.
 *
 * @since 1.0.0
 */
@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    /**
     * Repository for accessing inventory data.
     */
    private final InventoryRepository inventoryRepository;

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Repository for accessing warehouse data.
     */
    private final WarehouseRepository warehouseRepository;

    /**
     * Dependency injection constructor for InventoryQueryServiceImpl.
     * @param inventoryRepository the repository for accessing inventory data
     */
    public InventoryQueryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Optional<Inventory> handle(GetInventoryByIdQuery query) {
        return inventoryRepository.findById(query.inventoryId());
    }

    @Override
    public Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery query) {
        var product = productRepository.findById(query.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(query.productId())));
        var warehouse = warehouseRepository.findById(query.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(query.warehouseId())));
        var targetBestBeforeDate = new ProductBestBeforeDate(query.bestBeforeDate());

        return inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate);
    }

    @Override
    public Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdQuery query) {
        var product = productRepository.findById(query.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(query.productId())));
        var warehouse = warehouseRepository.findById(query.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(query.warehouseId())));

        return inventoryRepository.findByProductAndWarehouse(product, warehouse);
    }

    @Override
    public List<Inventory> handle(GetAllProductsByWarehouseIdQuery query) {
        return inventoryRepository.findByWarehouseId(query.warehouseId());
    }
}
