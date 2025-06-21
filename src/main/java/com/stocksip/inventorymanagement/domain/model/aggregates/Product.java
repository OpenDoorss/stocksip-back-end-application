package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.*;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.stocksip.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Optional;

/**
 * Product Aggregate Root
 *
 * @summary
 * The Product class is an aggregate root that represents a product in a warehouse.
 * It is responsible for handling the CreateProductCommand command.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends AuditableAbstractAggregateRoot<Product> {

    /**
     * The type of liquor the product is made of.
     */
    @Column(nullable = false)
    @Getter
    private LiquorType liquorType;

    /**
     * The brand of the product.
     * This is a reference to the Brand name of the product.
     */
    @Column(nullable = false)
    @Getter
    private BrandName brandName;

    /**
     * The full name of the product.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private ProductName productName;

    /**
     * The price the product can be sold to the customers of the liquor shop.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private Money unitPrice;

    /**
     * The minimum number of products which can exist in a warehouse before generating an alert to the user.
     * It's used to generate alerts in the platform.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private ProductMinimumStock minimumStock;

    /**
     * The image URL of the product.
     * This is a value object that encapsulates the image URL.
     */
    @Getter
    @Setter
    private ImageUrl imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Inventory> inventories;

    /**
     * The identifier of the user who provided this product to the liquor store owner.
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private ProviderId providerId;

    protected Product() {
        // Default constructor for JPA
    }

    /**
     * Constructor to create a new Product instance.
     *
     * @param providerId The identifier of the provider who provided this product.
     * @param brand The brand of the product.
     * @param liquorType The type of liquor the product is made of.
     * @param additionalName Optional additional name for the product.
     * @param price The price of the product in PEN currency.
     * @param minimumStock The minimum stock of the product in the warehouse.
     * @param imageUrl The image URL of the product.
     */
    public Product(ProviderId providerId, String brand, String liquorType,
                   Optional<String> additionalName, double price,
                   int minimumStock, String imageUrl) {
        this.productName = new ProductName(BrandName.valueOf(brand.toUpperCase()), LiquorType.valueOf(liquorType.toUpperCase()), additionalName);
        this.minimumStock = new ProductMinimumStock(minimumStock);
        this.unitPrice = new Money(price, "PEN");
        this.liquorType = LiquorType.valueOf(liquorType.toUpperCase());
        this.brandName = BrandName.valueOf(brand.toUpperCase());
        this.providerId = providerId;
        this.imageUrl = setDefaultImageUrlIfNotProvided(imageUrl);
        this.inventories = List.of();
    }

    /**
     * Default constructor for handling commands
     *
     * @param command The command containing the information to create a new product.
     */
    public Product(CreateProductCommand command) {
        this.productName = new ProductName(BrandName.valueOf(command.brandName().toUpperCase()), LiquorType.valueOf(command.liquorType().toUpperCase()), command.additionalName());
        this.minimumStock = new ProductMinimumStock(command.minimumStock());
        this.unitPrice = new Money(command.unitPriceAmount(), "PEN");
        this.liquorType = LiquorType.valueOf(command.liquorType().toUpperCase());
        this.brandName = BrandName.valueOf(command.brandName().toUpperCase());
        this.providerId = command.providerId();
        this.imageUrl = setDefaultImageUrlIfNotProvided(command.imageUrl());
        this.inventories = List.of();
    }

    /**
     * Method to set a default image URL if the provided image URL is null or blank.
     * @param imageUrl The image URL to be set for the product.
     * @return The ImageUrl instance, either the provided one or a default one.
     */
    private ImageUrl setDefaultImageUrlIfNotProvided(String imageUrl) {
        return imageUrl == null || imageUrl.isBlank()
                ? ImageUrl.defaultImageUrl()
                : new ImageUrl(imageUrl);
    }

    /**
     * Sets the minimum stock for the product.
     * @param newMinimumStock The new minimum stock to be set for the product.
     */
    public void setMinimumStock(int newMinimumStock) {
        this.minimumStock = this.minimumStock.updateMinimumStock(newMinimumStock);
    }


    /**
     * Updates the product information such as price, minimum stock, and image URL.
     * @param updatedPrice The new price of the product, must be greater than zero.
     * @param updatedMinimumStock The new minimum stock of the product, must be a non-negative integer.
     * @param updatedImageUrl The new image URL of the product.
     */
    public void updateInformation(double updatedPrice, int updatedMinimumStock, String updatedImageUrl) {
        if (updatedPrice < 0) {
            throw new IllegalArgumentException("Updated price must be greater than zero.");
        }

        setMinimumStock(updatedMinimumStock);
        this.imageUrl = setDefaultImageUrlIfNotProvided(updatedImageUrl);
        this.unitPrice = new Money(updatedPrice, "PEN");
    }

    /**
     * Verifies if the product has an inventory relation with the specified inventory.
     *
     * @param inventory The inventory to check for a relation with the product.
     * @return True if the product has a relation with the specified inventory; otherwise, false.
     */
    private boolean existsWarehouseRelation(Inventory inventory) {
        return inventories.contains(inventory);
    }

    /**
     * This method adds a warehouse relation to the product.
     *
     * @param inventory The inventory to be added to the product's inventory relations.
     */
    public void addWarehouseRelation(Inventory inventory) {
        if (existsWarehouseRelation(inventory)) return;
        inventories.add(inventory);
    }

    /**
     * This method removes a warehouse relation from the product.
     *
     * @param inventory The inventory to be removed from the product's inventory relations.
     */
    public void removeWarehouseRelation(Inventory inventory) {
        inventories.remove(inventory);
    }
}
