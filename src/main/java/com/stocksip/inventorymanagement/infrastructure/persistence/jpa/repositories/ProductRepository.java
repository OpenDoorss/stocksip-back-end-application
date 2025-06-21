package com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query("SELECT i.product FROM Inventory i WHERE i.warehouse.profileId = :profileId")
    List<Product> findAllProductsByProfileId(@Param("profileId") Long profileId);

    @Query("""
                SELECT i.product
                FROM Inventory i
                WHERE i.warehouse.warehouseId = :warehouseId
                    AND i.product.providerId = :providerId
   """)
    List<Product> findAllProductsByWarehouseIdAndProviderId(@Param("warehouseId") Long warehouseId, @Param("providerId") Long providerId);

    @Query("SELECT i.product FROM Inventory i WHERE i.warehouse.warehouseId = :warehouseId")
    List<Product> findAllProductsByWarehouseId(@Param("warehouseId") Long warehouseId);

    @Query("""
                SELECT i.product
                FROM Inventory i
                WHERE i.product.id = :id
                    AND i.productBestBeforeDate = :bestBeforeDate
                    AND i.warehouse.warehouseId = :warehouseId
    """)
    Optional<Product> findProductByIdAndWarehouseIdAndBestBeforeDate(@Param("id") Long id, @Param("warehouseId") Long warehouseId, @Param("bestBeforeDate") Long bestBeforeDate);


}
