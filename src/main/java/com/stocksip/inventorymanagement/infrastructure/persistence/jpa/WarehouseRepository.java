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
     * @param street     the street of the warehouse address
     * @param city       the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @return true if a warehouse with the specified address exists, false otherwise
     */
    boolean existsByAddressStreetAndAddressCityAndAddressPostalCode(String street, String city, String postalCode);

    /**
     * Checks if a warehouse exists by its name and a different warehouse ID.
     *
     * @param name the name of the warehouse
     * @param warehouseId the unique identifier of the warehouse
     * @return true if a warehouse with the specified name exists and is not the one with the given ID, false otherwise
     */
    boolean existsByNameAndWarehouseIdIsNot(String name, Long warehouseId);

    /**
     * Checks if a warehouse exists by its address details, excluding a specific warehouse ID.
     *
     * @param street     the street of the warehouse address
     * @param city       the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @param warehouseId         the ID of the warehouse to exclude from the check
     * @return true if a warehouse with the specified address exists and is not the one with the given ID, false otherwise
     */
    boolean existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndWarehouseIdIsNot(String street, String city, String postalCode, Long warehouseId);
}
