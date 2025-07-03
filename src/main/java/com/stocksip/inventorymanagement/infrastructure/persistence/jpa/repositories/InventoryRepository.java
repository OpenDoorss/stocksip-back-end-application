package com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Inventory entity.
 *
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for the inventory entity.
 * It allows for easy interaction with the database, enabling operations such as saving, deleting, and finding inventories.
 *
 * @since 1.0.0
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * This method retrieves an inventory item by its product ID and warehouse ID.
     * @param product The ID of the product whose inventory is to be retrieved.
     * @param warehouse The ID of the warehouse where the product's inventory is located.
     * @return A task that returns an Inventory object if found, or null if not found.
     */
    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);

    /**
     * This method retrieves an inventory item by its product ID and warehouse ID and Best Before Date.
     * @param product The ID of the product whose inventory is to be retrieved.
     * @param warehouse The ID of the warehouse where the product's inventory is located.
     * @param bestBefore The expiration date of the product's inventory.
     * @return A task that returns an Inventory object if found, or null if not found.
     */
    Optional<Inventory> findByProductAndWarehouseAndProductBestBeforeDate(Product product, Warehouse warehouse, ProductBestBeforeDate bestBefore);

    /**
     * This method checks if there is an inventory item that links a product with a warehouse.
     * @param product The product used to check.
     * @param warehouse The warehouse used to check.
     * @return True if exists an inventory that links a specific warehouse and product, otherwise, false.
     */
    boolean existsByProductAndWarehouse(Product product, Warehouse warehouse);

    /**
     * This method checks if there is an inventory item that links a product with a warehouse.
     * @param productId - The ID of the product used to check.
     * @param warehouse - The ID of the warehouse used to check.
     * @return - True if exists an inventory that links a specific warehouse and product, otherwise, false.
     */
    boolean existsByProduct_ProductIdAndWarehouse_WarehouseId(Long productId, Long warehouse);

    /**
     * This method retrieves all inventory items for a specific warehouse.
     * @param warehouseId - The ID of the warehouse for which to retrieve inventory items.
     * @return - A list of Inventory objects associated with the specified warehouse.
     */
    @Query("SELECT i FROM Inventory i JOIN FETCH i.product WHERE i.warehouse.warehouseId = :warehouseId")
    List<Inventory> findByWarehouseId(@Param("warehouseId") Long warehouseId);
}
