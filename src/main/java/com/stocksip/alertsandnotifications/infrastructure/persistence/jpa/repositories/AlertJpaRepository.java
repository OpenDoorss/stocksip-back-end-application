package com.stocksip.alertsandnotifications.infrastructure.persistence.jpa.repositories;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProductId;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProfileId;
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
     * This method retrieves all alerts associated with a specific profile ID.
     *
     * @param profileId The ID of the profile whose alerts are to be retrieved.
     * @return The list of alerts that belong to the specified profile ID.
     */
    @Query("SELECT a FROM Alert a WHERE a.profileId = :profileId")
    List<Alert> findByProfileId(@Param("profileId") ProfileId profileId);

    /**
     * This method retrieves all alerts associated with a specific warehouse ID.
     *
     * @param warehouseId The ID of the warehouse whose alerts are to be retrieved.
     * @return A list of alerts that belong to the specified warehouse ID.
     */
    @Query("SELECT a FROM Alert a WHERE a.warehouseId = :warehouseId")
    List<Alert> findByWarehouseId(@Param("warehouseId") WarehouseId warehouseId);
} 