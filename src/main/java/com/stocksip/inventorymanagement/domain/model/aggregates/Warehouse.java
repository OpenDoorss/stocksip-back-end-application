package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;

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
@Getter
public class Warehouse {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

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
            @AttributeOverride(name = "street", column = @Column(nullable = false)),
            @AttributeOverride(name = "city", column = @Column(nullable = false)),
            @AttributeOverride(name = "state", column = @Column(nullable = false)),
            @AttributeOverride(name = "postalCode", column = @Column(nullable = false)),
            @AttributeOverride(name = "country", column = @Column(nullable = false))
    })
    private WarehousesAddress address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minTemperature", column = @Column(nullable = false)),
            @AttributeOverride(name = "maxTemperature", column = @Column(nullable = false))
    })
    private WarehouseTemperature temperature;

    /**
     * The total capacity of this warehouse in cubic meters.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "capacity", column = @Column(nullable = false))
    })
    private WarehouseCapacity capacity;

    /**
     * The url of the image that shows with the warehouse
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    })
    private ImageUrl imageUrl;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "profileId", column = @Column(name = "profile_id", nullable = false))
    })
    private AccountId accountId;

    // Default constructor for JPA
    protected Warehouse() {}

    /**
     * Constructor to create a new Warehouse instance.
     * This constructor initializes the warehouse with the provided command details.
     *
     * @param command the command containing the details for creating a new warehouse.
     */
    public Warehouse(CreateWarehouseCommand command) {
        this.name = command.name();
        this.address = new WarehousesAddress(command.street(), command.city(), command.district(), command.postalCode(), command.country());
        this.temperature = new WarehouseTemperature(command.minTemperature(), command.maxTemperature());
        this.capacity = new WarehouseCapacity(command.capacity());
        this.imageUrl = this.setDefaultImageUrlIfNotProvided(command.imageUrl());
        this.accountId = new AccountId(command.accountId());
    }

    /**
     * Updates the warehouse information with the details provided in the command.
     *
     * @param command the command containing the updated details for the warehouse.
     * @return the updated Warehouse instance.
     */
    public Warehouse updateInformation(UpdateWarehouseCommand command) {
        this.name = command.name();
        this.address = new WarehousesAddress(command.street(), command.city(), command.district(), command.postalCode(), command.country());
        this.temperature = new WarehouseTemperature(command.minTemperature(), command.maxTemperature());
        this.capacity = new WarehouseCapacity(command.capacity());
        this.imageUrl = setDefaultImageUrlIfNotProvided(command.imageUrl());
        return this;
    }

    /**
     * Set the image URL for the warehouse.
     * If the provided image URL is null or blank, it sets a default image URL.
     *
     * @param imageUrl the URL of the image representing the warehouse
     * @return an ImageUrl object containing the image URL
     */
    private ImageUrl setDefaultImageUrlIfNotProvided(String imageUrl) {
        return imageUrl == null || imageUrl.isBlank()
                ? ImageUrl.defaultImageUrl()
                : new ImageUrl(imageUrl);
    }

}
