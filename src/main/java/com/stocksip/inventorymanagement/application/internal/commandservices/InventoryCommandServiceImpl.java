package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.commands.*;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductStock;
import com.stocksip.inventorymanagement.domain.services.InventoryCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.InventoryRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;

import java.util.Optional;

/**
 * InventoryCommandServiceImpl
 *
 * @summary
 * InventoryCommandServiceImpl is an implementation of the InventoryCommandService interface.
 *
 * @since 1.0.0
 */
public class InventoryCommandServiceImpl implements InventoryCommandService {

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Repository for accessing inventory data.
     */
    private final InventoryRepository inventoryRepository;

    /**
     * Repository for accessing warehouse data.
     */
    private final WarehouseRepository warehouseRepository;

    public InventoryCommandServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository, WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Optional<Inventory> handle(AddStockToProductCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate);

        if (inventoryToUpdate.isEmpty()) {
            try {
                var targetAddedQuantity = new ProductStock(command.addedQuantity());
                var newInventory = new Inventory(product, warehouse, targetAddedQuantity, targetBestBeforeDate);
                product.addWarehouseRelation(newInventory);
                productRepository.save(product);
                var inventoryCreated = inventoryRepository.save(newInventory);
                return Optional.of(inventoryCreated);
            } catch (Exception e) {
                throw new RuntimeException("Error creating inventory: " + e.getMessage(), e);
            }
        } else {
            try {
                Inventory inventory = inventoryToUpdate.get();
                inventory.addStockToProduct(command.addedQuantity());
                var inventoryUpdated = inventoryRepository.save(inventory);
                return Optional.of(inventoryUpdated);
            } catch (Exception e) {
                throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Inventory> handle(ReduceStockFromProductCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        //TODO: Add code to create a new product exit.

        try {
            inventoryToUpdate.reduceStockFromProduct(command.removedQuantity());
            var inventoryUpdated = inventoryRepository.save(inventoryToUpdate);
            return Optional.of(inventoryUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Inventory> handle(AddProductsToWarehouseCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());
        var targetProductStock = new ProductStock(command.quantity());
        var inventory = new Inventory(product, warehouse, targetProductStock, targetBestBeforeDate);

        try {
            product.addWarehouseRelation(inventory);
            productRepository.save(product);
            var inventoryCreated = inventoryRepository.save(inventory);
            return Optional.of(inventoryCreated);
        } catch (Exception e) {
            throw new RuntimeException("Error creating inventory: " + e.getMessage(), e);
        }
    }

    @Override
    public Long handle(MoveProductToAnotherWarehouseCommand command) {
        var productToMove = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var newWarehouse = warehouseRepository.findById(command.newWarehouseId());

        return 0l;
    }

    @Override
    public Long handle(DeleteProductFromWarehouseCommand command) {

        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        var inventoryToDelete = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        if (inventoryToDelete.getProductStock().stock() == 0) {
            try {
                product.removeWarehouseRelation(inventoryToDelete);
                productRepository.save(product);
                inventoryRepository.delete(inventoryToDelete);
                return product.getId();
            } catch (Exception e) {
                throw new RuntimeException("Error updating product: " + e.getMessage(), e);
            }
        } else {
            throw  new IllegalArgumentException("Cannot delete an inventory if it has stock remaining.");
        }
    }
}
