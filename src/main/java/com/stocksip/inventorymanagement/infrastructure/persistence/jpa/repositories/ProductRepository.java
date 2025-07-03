package com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.valueobjects.BrandName;
import com.stocksip.inventorymanagement.domain.model.valueobjects.LiquorType;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Product entity.
 *
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for the Product entity.
 * It allows for easy interaction with the database, enabling operations such as saving, deleting, and finding products by their ID.
 *
 * @since 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * This method is used to retrieve all products associated with a specific account ID.
     * @param accountId The ID of the account whose products are to be retrieved.
     * @return The list of products associated with the specified account ID.
     */
    @Query("SELECT p FROM Product p WHERE p.accountId.accountId = :accountId")
    List<Product> findAllProductsByAccountId(@Param("accountId") Long accountId);

    /**
     * This method retrieves all products associated with a specific provider and warehouse ID.
     * @param warehouseId The ID of the warehouse whose products are to be retrieved.
     * @param accountId The ID of the provider whose products in a specific warehouse are to be retrieved.
     * @return A list of products that belong to the specified provider and warehouse ID.
     */
    @Query("""
                SELECT i.product
                FROM Inventory i
                WHERE i.warehouse.warehouseId = :warehouseId
                    AND i.product.accountId.accountId = :accountId
    """)
    List<Product> findAllProductsByWarehouseIdAndAccountId(@Param("warehouseId") Long warehouseId, @Param("accountId") Long accountId);

    /**
     * This method retrieves all products associated with a specific warehouse ID.
     * @param warehouseId The ID of the warehouse whose products are to be retrieved.
     * @return A list of products that belong to the specified warehouse.
     */
    @Query("SELECT i.product FROM Inventory i WHERE i.warehouse.warehouseId = :warehouseId")
    List<Product> findAllProductsByWarehouseId(@Param("warehouseId") Long warehouseId);

    /**
     * This method retrieves a product by its ID, warehouse ID, and best before date.
     * @param id The ID of the product to be retrieved.
     * @param warehouseId The ID of the warehouse where the product is located.
     * @param bestBeforeDate The best before date of the product's inventory.
     * @return The Product if found, or null if not found.
     */
    @Query("""
                SELECT i.product
                FROM Inventory i
                WHERE i.product.productId = :id
                    AND i.productBestBeforeDate = :bestBeforeDate
                    AND i.warehouse.warehouseId = :warehouseId
    """)
    Optional<Product> findProductByIdAndWarehouseIdAndBestBeforeDate(@Param("id") Long id, @Param("warehouseId") Long warehouseId, @Param("bestBeforeDate") Date bestBeforeDate);

    /**
     * This method retrieves all product items that match the specified full name and warehouse ID.
     * @param warehouseId The ID of the warehouse where the product is stored.
     * @param brandName The name of the brand of the product.
     * @param liquorType The type of liquor of the product.
     * @param productName The additional name of the product, if any.
     * @return A Product object that match the specified criteria with its correspondent Inventory object.
     */
    @Query("""
                SELECT i.product
                FROM Inventory i
                WHERE i.warehouse.warehouseId = :warehouseId
                    AND i.product.brandName = :brandName
                    AND i.product.liquorType = :liquorType
                    AND i.product.productName = :productName
    """)
    Optional<Product> findProductByBrandNameAndLiquorTypeAndProductNameAndWarehouseId(@Param("warehouseId") Long warehouseId, @Param("brandName") String brandName, @Param("liquorType") String liquorType, @Param("productName") String productName);

    /**
     * This method checks if a product with the specified ID exists in the database.
     * @param id The ID of the product to check for existence.
     * @return True if a product with the specified ID exists; otherwise, false.
     */
    boolean existsById(Long id);

    /**
     * This method checks if a product with the specified full name (brand name, liquor type, and additional name) exists in the database.
     * @param brandName The name of the brand.
     * @param liquorType The liquor type of the product.
     * @param productName The additional name of the product.
     * @return True if a product exists, otherwise, false.
     */
    boolean existsByBrandNameAndLiquorTypeAndProductName(BrandName brandName, LiquorType liquorType, ProductName productName);

    /**
     * This method retrieves the image URL of a product by its ID.
     * @param productId The ID of the product whose image URL is to be retrieved.
     * @return The image URL of the product if found, otherwise null.
     */
    @Query("SELECT p.imageUrl.imageUrl FROM Product p WHERE p.productId = :productId")
    String findImageUrlByProductId(@Param("productId") Long productId);
}
