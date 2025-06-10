package com.stocksip.inventorymanagement.infrastructure.persistence.jpa;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
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
     * Checks if a warehouse with the given name exists, ignoring case.
     *
     * @param name the name of the warehouse to check
     * @return true if a warehouse with the given name exists, false otherwise
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Checks if a warehouse with the given name exists, excluding the warehouse with the specified ID.
     *
     * @param name the name of the warehouse to check
     * @param warehouseId the ID of the warehouse to exclude from the check
     * @return true if a warehouse with the given name exists and is not the specified warehouse, false otherwise
     */
    boolean existsByNameAndWarehouseIdIsNot(String name, Long warehouseId);

    /**
     * Checks if a warehouse exists by its address components, ignoring case.
     *
     * @param street the street of the warehouse address
     * @param city the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @return true if a warehouse with the given address exists, false otherwise
     */
    boolean existsByAddressStreetAndAddressCityAndAddressPostalCodeIgnoreCase(String street, String city, String postalCode);

    /**
     * Checks if a warehouse exists by its address components, excluding the warehouse with the specified ID.
     *
     * @param street the street of the warehouse address
     * @param city the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @param warehouseId the ID of the warehouse to exclude from the check
     * @return true if a warehouse with the given address exists and is not the specified warehouse, false otherwise
     */
    boolean existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndWarehouseIdIsNot(String street, String city, String postalCode, Long warehouseId);
}
