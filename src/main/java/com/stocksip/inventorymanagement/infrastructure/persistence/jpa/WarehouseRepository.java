package com.stocksip.inventorymanagement.infrastructure.persistence.jpa;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Warehouse entity.
 *
 * @summary
 * This interface extends JpaRepository to provide CRUD operations for the Warehouse entity.
 * It allows for easy interaction with the database, enabling operations such as saving, deleting, and finding warehouses by their ID.
 *
 * @since 1.0.0
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    /**
     * Checks if a warehouse exists by its address details.
     *
     * @param street the street of the warehouse address
     * @param city the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @return true if a warehouse with the specified address exists, false otherwise
     */
    boolean existsByAddressStreetAndAddressCityAndAddressPostalCode(String street, String city, String postalCode);
}
