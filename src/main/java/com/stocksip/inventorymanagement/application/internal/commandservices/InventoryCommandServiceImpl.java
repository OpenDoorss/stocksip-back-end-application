package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.commands.*;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductStock;
import com.stocksip.inventorymanagement.domain.services.InventoryCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.InventoryRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * InventoryCommandServiceImpl
 *
 * @summary
 * InventoryCommandServiceImpl is an implementation of the InventoryCommandService interface.
 *
 * @since 1.0.0
 */
@Service
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

    /**
     * Handles the command for adding stock to a product in a warehouse.
     *
     * @param command The command containing the details for adding stock to a product.
     * @return The updated inventory object.
     */
    @Override
    public Optional<Inventory> handle(AddStockToProductCommand command) {

        // Validate if the stock of the product to be added exists.
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        // Validate if the warehouse where the stock will be added exists.
        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        // Retrieves the inventory of the product in the warehouse if it exists, if not, it will create a new inventory entry with the new Expiration Date.
        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouse(product, warehouse);

        try {
            Inventory inventory = inventoryToUpdate.get();
            inventory.addStockToProduct(command.addedQuantity());
            inventory.updatedBestBeforeDate(command.bestBeforeDate());
            var inventoryUpdated = inventoryRepository.save(inventory);
            return Optional.of(inventoryUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for reducing stock of a product in a specific warehouse.
     * In other words, updating an inventory object.
     *
     * @param command The command containing the details for reducing stock from a product.
     * @return The updated inventory object.
     */
    @Override
    public Optional<Inventory> handle(ReduceStockFromProductCommand command) {

        // Validate if the stock of the product to decrease exists.
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        // Validate if the warehouse where the stock will be decreased exists.
        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        // Retrieves the inventory of the product in the warehouse if it exists.
        var inventoryToUpdate = inventoryRepository.findByProductAndWarehouse(product, warehouse)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        // Registers a product exit with the provided command details.
        //TODO: Add code to create a new product exit.

        // If the retrieved inventory exists, it decreases the stock to the current inventory.
        try {
            inventoryToUpdate.reduceStockFromProduct(command.removedQuantity());
            inventoryToUpdate.updatedBestBeforeDate(command.bestBeforeDate());
            var inventoryUpdated = inventoryRepository.save(inventoryToUpdate);
            return Optional.of(inventoryUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for creating an inventory object that links a specific product and a specific warehouse.
     *
     * @param command The command containing the details for creating a new inventory entry.
     * @return The created inventory object if created, null if not.
     */
    @Override
    public Optional<Inventory> handle(AddProductsToWarehouseCommand command) {

        // Validate if the product to be added exists.
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        // Validate if the warehouse where the product will be added exists.
        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        if (inventoryRepository.existsByProduct_ProductIdAndWarehouse_WarehouseId(command.productId(), command.warehouseId())) {
            throw new IllegalArgumentException("Product with ID %s already exists in warehouse with ID %s.".formatted(command.productId(), command.warehouseId()));
        }

        // Creates a new inventory entry for the product in the warehouse with the specified quantity.
        var inventory = new Inventory(product, warehouse, command.quantity(), command.bestBeforeDate());

        // Adds the new inventory entry to the product's inventory relations.
        // Completes the inventory creation by saving the changes to the database.
        try {
            var inventoryCreated = inventoryRepository.save(inventory);
            return Optional.of(inventoryCreated);
        } catch (Exception e) {
            throw new RuntimeException("Error creating inventory: " + e.getMessage(), e);
        }
    }

    /**
     * This method handles the command for moving stock from a current warehouse to another warehouse.
     *
     * @param command The command containing the details for moving stock.
     * @return The ID of the product whose stock is being moved to another warehouse.
     */
    @Override
    public Optional<Inventory> handle(MoveProductToAnotherWarehouseCommand command) {

        // Validate if the product to be moved exists.
        var productToMove = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        // Validate if the new warehouse where the product will be moved exists.
        var newWarehouse = warehouseRepository.findById(command.newWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.productId())));

        // Validate if the old warehouse where the product stock was exists.
        var oldWarehouse = warehouseRepository.findById(command.oldWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.productId())));

        // Validate if the old warehouse where the product will be moved exists.
        if (command.newWarehouseId().equals(command.oldWarehouseId())) {
            throw new IllegalArgumentException("Cannot move products to the same warehouse.");
        }

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        // Retrieves the current inventory of the product in the old warehouse.
        var currentInventory = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(
                productToMove,
                oldWarehouse,
                targetBestBeforeDate
        ).orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        // Removes the moved stock from the current inventory. And If the current inventory has no stock left, the product state will be set to OUT_OF_STOCK.
        currentInventory.reduceStockFromProduct(command.quantityToMove());

        // Registers a new product movement with the amount of stock exiting.
        //TODO: Add code to create a Product Movement.

        // Saves the changes to the current repository
        inventoryRepository.save(currentInventory);

        // Retrieves the inventory of the product in the new warehouse if it exists.
        var newInventory = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(
                productToMove,
                newWarehouse,
                targetBestBeforeDate
        );

        // If the retrieved inventory does not exist, creates a new inventory entry with the moved quantity.
        if (newInventory.isEmpty()) {
            try {
                var targetAddedQuantity = new ProductStock(command.quantityToMove());
                var newMovedInventory = new Inventory(productToMove, newWarehouse, command.quantityToMove(), command.bestBeforeDate());

                // Adds the new inventory entry to the product's inventory relations if it does not already exist.
                productToMove.addWarehouseRelation(newMovedInventory);

                productRepository.save(productToMove);
                inventoryRepository.save(newMovedInventory);
                return Optional.of(newMovedInventory);
            } catch (Exception e) {
                throw new RuntimeException("Error moving products: " + e.getMessage(), e);
            }
        }

        // If the retrieved inventory already existed, it adds the moved stock to the retrieved inventory.
        else {
            try {
                Inventory inventory = newInventory.get();
                inventory.addStockToProduct(command.quantityToMove());
                inventoryRepository.save(inventory);
                return Optional.of(inventory);
            } catch (Exception e) {
                throw new RuntimeException("Error moving products: " + e.getMessage(), e);
            }
        }
    }

    /**
     * This method handles the command to delete an inventory.
     *
     * @param command The command containing the details for deleting an inventory.
     * @return The ID of the product whose inventory is being deleted.
     */
    @Override
    public Long handle(DeleteProductFromWarehouseCommand command) {

        // Validate if the product to be deleted exists.
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product with ID %s does not exist".formatted(command.productId())));

        // Validate if the warehouse where the product will be deleted exists.
        var warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        var targetBestBeforeDate = new ProductBestBeforeDate(command.bestBeforeDate());

        // Retrieves the inventory of the product in the warehouse if it exists.
        var inventoryToDelete = inventoryRepository.findByProductAndWarehouseAndProductBestBeforeDate(product, warehouse, targetBestBeforeDate)
                .orElseThrow(() -> new IllegalArgumentException("Inventory does not exists."));

        product.removeWarehouseRelation(inventoryToDelete);
        productRepository.save(product);



        // If the current stock of the product in the warehouse is not zero, it throws an exception to prevent deletion.
        // If the retrieved inventory exists, it removes the relation between the product and the inventory.
        if (inventoryToDelete.getProductStock().stock() == 0) {
            try {
                inventoryRepository.delete(inventoryToDelete);
                return product.getProductId();
            } catch (Exception e) {
                throw new RuntimeException("Error updating product: " + e.getMessage(), e);
            }
        } else {
            throw  new IllegalArgumentException("Cannot delete an inventory if it has stock remaining.");
        }
    }
}
