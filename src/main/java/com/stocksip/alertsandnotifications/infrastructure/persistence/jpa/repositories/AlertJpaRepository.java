package com.stocksip.alertsandnotifications.infrastructure.persistence.jpa.repositories;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.AccountId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId;
import com.stocksip.alertsandnotifications.domain.repositories.AlertRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class implements the AlertRepository interface, providing methods to interact with the Alert aggregate.
 *
 * @summary
 * JPA repository implementation for Alert aggregates that provides methods
 * for persisting and retrieving alert data from the database.
 *
 * @since 1.0
 */
@Repository
public interface AlertJpaRepository extends JpaRepository<Alert, Long>, AlertRepository {

    /**
     * This method retrieves all alerts associated with a specific product ID.
     *
     * @param productId The ID of the product whose alerts are to be retrieved.
     * @return A list of alerts that belong to the specified product ID.
     */
    @Query("SELECT a FROM Alert a WHERE a.productId = :productId")
    List<Alert> findByProductId(@Param("productId") ProductId productId);

    /**
     * This method retrieves all alerts associated with a specific account ID.
     *
     * @param accountId The ID of the profile whose alerts are to be retrieved.
     * @return The list of alerts that belong to the specified profile ID.
     */
    @Query("SELECT a FROM Alert a WHERE a.accountId = :accountId")
    List<Alert> findByAccountId(@Param("accountId") AccountId accountId);

    /**
     * This method retrieves all alerts associated with a specific warehouse ID.
     *
     * @param warehouseId The ID of the warehouse whose alerts are to be retrieved.
     * @return A list of alerts that belong to the specified warehouse ID.
     */
    @Query("SELECT a FROM Alert a WHERE a.warehouseId = :warehouseId")
    List<Alert> findByWarehouseId(@Param("warehouseId") WarehouseId warehouseId);

    /**
     * Finds all active alerts of a given type for a specific product and warehouse.
     *
     * @param type The type of the alert (e.g., EXPIRATION_WARNING)
     * @param productId The product ID
     * @param warehouseId The warehouse ID
     * @return A list of active alerts matching the criteria
     */
    @Query("SELECT a FROM Alert a WHERE a.type = :type AND a.productId = :productId AND a.warehouseId = :warehouseId AND a.state = com.stocksip.alertsandnotifications.domain.model.aggregates.Alert.AlertState.ACTIVE")
    List<Alert> findActiveByTypeProductWarehouse(@Param("type") com.stocksip.alertsandnotifications.domain.model.aggregates.Alert.AlertTypes type,
                                                 @Param("productId") com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId productId,
                                                 @Param("warehouseId") com.stocksip.alertsandnotifications.domain.model.valueobjects.WarehouseId warehouseId);
} 