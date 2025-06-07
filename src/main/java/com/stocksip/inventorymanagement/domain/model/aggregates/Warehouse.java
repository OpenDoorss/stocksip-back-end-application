package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseCapacity;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehouseTemperature;
import com.stocksip.inventorymanagement.domain.model.valueobjects.WarehousesAddress;
import com.stocksip.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Warehouse Aggregate
 *
 * @summary
 * The Warehouse class is an aggregate that represents an inventory in a liquor shop.
 * It contains information about the warehouse such as its name, address, temperature range, total capacity, and an image URL.
 *
 * @since 1.0.0
 */
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long warehouseId;

    /**
     * Unique identifier of the profile that owns this warehouse
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "profileId", column = @Column(name = "profile_id", nullable = false))
    })
    private ProfileId profileId;

    /**
     * Name the owner gives to this warehouse
     */
    @Column(nullable = false, length = 100)
    @Getter
    private String name;

    /**
     * The location where this warehouse is located.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street", nullable = false)),
            @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false)),
            @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code", nullable = false)),
            @AttributeOverride(name = "country", column = @Column(name = "country", nullable = false))
    })
    private WarehousesAddress address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minTemperature", column = @Column(name = "min_temperature", nullable = false)),
            @AttributeOverride(name = "maxTemperature", column = @Column(name = "max_temperature", nullable = false))
    })
    private WarehouseTemperature temperature;

    /**
     * The total capacity of this warehouse in cubic meters.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "capacity", column = @Column(name = "capacity", nullable = false))
    })
    private WarehouseCapacity capacity;

    /**
     * The url of the image that shows with the warehouse
     */
    private String imageUrl;

    // Default constructor for JPA
    protected Warehouse() {}

    /**
     * Constructor to create a new Warehouse instance.
     * This constructor initializes the warehouse with the provided command details.
     *
     * @param command the command containing the details for creating a new warehouse.
     */
    public Warehouse(CreateWarehouseCommand command) {
        this.profileId = command.profileId();
        this.name = command.name();
        this.address = command.address();
        this.temperature = command.temperature();
        this.capacity = command.capacity();
        this.imageUrl = command.imageUrl();
    }

    /**
     * Update the warehouse information.
     * This method allows updating the warehouse's name, address, temperature, total capacity, and image URL.
     * @param name the new name of the warehouse.
     * @param address the new location of the warehouse.
     * @param newTotalCapacity the new total capacity of the warehouse.
     * @param imageUrl the url of the image.
     *
     * @return the updated Warehouse object
     */
    public Warehouse updateInformation(String name, WarehousesAddress address, WarehouseTemperature temperature, WarehouseCapacity newTotalCapacity, String imageUrl) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (address != null) {
            this.address = address;
        }
        if (temperature != null) {
            this.temperature = temperature;
        }
        if (newTotalCapacity != null) {
            this.capacity = newTotalCapacity;
        }
        if (!(imageUrl == null)) {
            this.imageUrl = imageUrl;
        }
        return this;
    }
}
