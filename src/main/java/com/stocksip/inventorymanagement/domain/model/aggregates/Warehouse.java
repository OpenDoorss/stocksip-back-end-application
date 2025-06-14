package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
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

    /**
     * The unique identifier of the warehouse.
     */
    @Id
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
    private WarehouseTemperature warehouseTemperature;

    /**
     * The total capacity of this warehouse in cubic meters.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "capacity", column = @Column(name = "capacity", nullable = false))
    })
    private WarehouseCapacity warehouseCapacity;

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
    private ProfileId profileId;

    // Default constructor for JPA
    protected Warehouse() {}


    /**
     * Constructor to create a new Warehouse instance.
     * This constructor initializes the warehouse with the provided command details.
     *
     * @param command the command containing the details for creating a new warehouse.
     */
    public Warehouse(CreateWarehouseCommand command, String imageUrl) {
        this.name = command.name();
        this.address = command.address();
        this.warehouseTemperature = command.warehouseTemperature();
        this.warehouseCapacity = command.warehouseCapacity();
        this.imageUrl = this.setDefaultImageUrlIfNotProvided(imageUrl);
        this.profileId = new ProfileId(command.profileId());
    }

    /**
     * Update the warehouse information.
     * This method allows updating the warehouse's name, address, temperature, total capacity, and image URL.
     * @param name the new name of the warehouse.
     * @param address the new location of the warehouse.
     * @param warehouseCapacity the new total capacity of the warehouse.
     * @param imageUrl the url of the image.
     *
     * @return the updated Warehouse object
     */
    public Warehouse updateInformation(String name, WarehousesAddress address, WarehouseTemperature warehouseTemperature, WarehouseCapacity warehouseCapacity, String imageUrl) {
        this.name = name;
        this.address = address;
        this.warehouseTemperature = warehouseTemperature;
        this.warehouseCapacity = warehouseCapacity;
        this.imageUrl = setDefaultImageUrlIfNotProvided(imageUrl);
        return this;
    }

    /**
     * Get the full address of the warehouse.
     * This method formats the address components into a single string.
     *
     * @return a string representing the full address of the warehouse.
     */
    public String getFullAddress() {
        return String.format("%s, %s, %s, %s, %s",
                address.street(),
                address.city(),
                address.district(),
                address.postalCode(),
                address.country());
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
