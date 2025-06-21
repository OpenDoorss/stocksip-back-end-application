package com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     * Checks if a warehouse with the given name exists for the specified profile ID, ignoring case.
     *
     * @param name the name of the warehouse to check
     * @param accountId the ID of the profile associated with the warehouse
     * @return true if a warehouse with the given name exists for the specified profile, false otherwise
     */
    boolean existsByNameIgnoreCaseAndProfileId(String name, AccountId accountId);

    /**
     * Checks if a warehouse with the given name exists for the specified profile ID, excluding a specific warehouse ID.
     *
     * @param name the name of the warehouse to check
     * @param profile the ID of the profile associated with the warehouse
     * @param warehouseId the ID of the warehouse to exclude from the check
     * @return true if a warehouse with the given name exists for the specified profile, excluding the specified warehouse ID, false otherwise
     */
    boolean existsByNameAndProfileIdAndWarehouseIdIsNot(String name, AccountId profile, Long warehouseId);

    /**
     * Checks if a warehouse exists by its address components and profile ID, ignoring case.
     *
     * @param street the street of the warehouse address
     * @param city the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @param accountId the ID of the profile associated with the warehouse
     * @return true if a warehouse with the given address exists for the specified profile, false otherwise
     */
    boolean existsByAddressStreetAndAddressCityAndAddressPostalCodeIgnoreCaseAndProfileId(String street, String city, String postalCode, AccountId accountId);

    /**
     * Checks if a warehouse exists by its address components, profile ID, and excludes a specific warehouse ID.
     *
     * @param street the street of the warehouse address
     * @param city the city of the warehouse address
     * @param postalCode the postal code of the warehouse address
     * @param accountId the ID of the profile associated with the warehouse
     * @param warehouseId the ID of the warehouse to exclude from the check
     * @return true if a warehouse with the given address exists, excluding the specified warehouse ID, false otherwise
     */
    boolean existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndProfileIdAndWarehouseIdIsNot(String street, String city, String postalCode, AccountId accountId, Long warehouseId);

    /**
     * Finds a warehouse by its ID and profile ID.
     *
     * @param warehouseId the ID of the warehouse to find
     * @param accountId the ID of the profile associated with the warehouse
     * @return an Optional containing the found Warehouse, or empty if not found
     */
    Optional<Warehouse> findWarehouseByWarehouseIdAndProfileId(Long warehouseId, AccountId accountId);

    /**
     * Finds all warehouses associated with a specific profile ID.
     *
     * @param accountId the ID of the profile to find warehouses for
     * @return a List of Warehouses associated with the specified profile ID
     */
    List<Warehouse> findAllByProfileId(AccountId accountId);
}
