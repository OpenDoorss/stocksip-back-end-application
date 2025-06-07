package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Warehouse Aggregate
 *
 * @summary
 * The Warehouse class is an aggregate that represents an inventory in a liquor shop.
 * It is responsible for handling the CreateWarehouseCommand command.
 *
 * @since 1.0.0
 */
@Entity
public class Warehouse extends AuditableAbstractAggregateRoot<Warehouse> {

    /**
     * Unique identifier of the profile that owns this warehouse
     */
    @Getter
    @Embedded
    private ProfileId profileId;

    /**
     * Name the owner gives to this warehouse
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * The location where this warehouse is located.
     */
    @Column(nullable = false)
    private String location;

    /**
     * The total capacity of this warehouse in cubic meters.
     */
    @Column(nullable = false)
    private double totalCapacity;

    /**
     * The url of the image that shows with the warehouse
     */
    private String imageUrl;

    protected Warehouse() {
        // Default constructor for JPA
    }

    /**
     * @summary Constructor.
     * It creates a new Warehouse instance based on the CreateWarehouseCommand command.
     *
     */
    //TODO: Update this with the CreateWarehouseCommand
    public Warehouse(String name, String location, double totalCapacity) {
        this.name = name;
        this.location = location;
        this.totalCapacity = totalCapacity;
    }

    public Warehouse(String name, String location, double totalCapacity, String imageUrl) {
        this.name = name;
        this.location = location;
        this.totalCapacity = totalCapacity;
        this.imageUrl = imageUrl;
    }

    /**
     * Update the warehouse information.
     *
     * @param name the new name of the warehouse.
     * @param location the new location of the warehouse.
     * @param newTotalCapacity the new total capacity of the warehouse.
     * @param imageUrl the url of the image.
     *
     * @return the updated Warehouse object
     */
    public Warehouse updateInformation(String name, String location, double newTotalCapacity, String imageUrl) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (location != null && !location.isBlank()) {
            this.location = location;
        }
        if (!(newTotalCapacity <= 0)) {
            this.totalCapacity = newTotalCapacity;
        }
        if (!(imageUrl == null)) {
            this.imageUrl = imageUrl;
        }

        return this;
    }
}
