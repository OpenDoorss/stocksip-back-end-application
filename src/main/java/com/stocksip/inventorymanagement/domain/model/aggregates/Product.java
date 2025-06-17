package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.events.CreateLowStockAlertEvent;
import com.stocksip.inventorymanagement.domain.model.valueobjects.*;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.stocksip.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
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
     * The state of the product, which can be either WITH STOCK or OUT OF STOCK.
     */
    @Column(nullable = false)
    @Getter
    private ProductState productState;

    /**
     * The full name of the product.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private ProductName productName;

    /**
     * The date from which the product will no longer be consumable.
     */
    @Column(nullable = false, updatable = false)
    private Date expirationDate;

    /**
     * The price the product can be sold to the customers of the liquor shop.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private Money unitPrice;

    /**
     * The number that represents the current stock of the product in the warehouse.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private ProductStock currentStock;

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

    /**
     * The warehouse where the product is stored.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

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
     * @param warehouse The warehouse where the product is stored.
     * @param providerId The identifier of the provider who provided this product.
     * @param brand The brand of the product.
     * @param liquorType The type of liquor the product is made of.
     * @param additionalName Optional additional name for the product.
     * @param expirationDate The date from which the product will no longer be consumable.
     * @param price The price of the product in PEN currency.
     * @param currentStock The current stock of the product in the warehouse.
     * @param minimumStock The minimum stock of the product in the warehouse.
     * @param imageUrl The image URL of the product.
     */
    public Product(Warehouse warehouse, ProviderId providerId, String brand, String liquorType,
                   Optional<String> additionalName, Date expirationDate, double price, int currentStock,
                   int minimumStock, String imageUrl) {
        this.productName = new ProductName(BrandName.valueOf(brand.toUpperCase()), LiquorType.valueOf(liquorType.toUpperCase()), additionalName);
        this.expirationDate = expirationDate;
        this.minimumStock = new ProductMinimumStock(minimumStock);
        this.currentStock = new ProductStock(currentStock);
        this.unitPrice = new Money(price, "PEN");
        this.liquorType = LiquorType.valueOf(liquorType.toUpperCase());
        this.brandName = BrandName.valueOf(brand.toUpperCase());
        this.productState = ProductState.WITH_STOCK; // Default state when a product is created

        this.warehouse = warehouse;
        this.providerId = providerId;
        this.imageUrl = setDefaultImageUrlIfNotProvided(imageUrl);
    }

    /**
     * Factory method to create a Product from a CreateProductCommand.
     *
     * @param command The command containing the product creation details.
     * @return A new Product instance.
     */


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
     * Adds stock to the product.
     * @param quantity The quantity of stock to be added to the product.
     */
    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Added quantity must be greater than zero.");
        }

        if (this.currentStock.getStock() == 0) {
            this.productState = ProductState.WITH_STOCK; // Ensure product state is updated to WITH_STOCK when adding stock.
        }

        this.currentStock = this.currentStock.addStock(quantity);

    }

    /**
     * Removes stock from the product.
     * @param quantity The quantity of stock to be removed from the product.
     * @throws IllegalArgumentException if the quantity is less than or equal to zero, or if there is insufficient stock.
     */
    public void removeStock(int quantity) {
        // Validate the quantity to be removed, if it is less than or equal to zero, throw an exception.
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        // Check if there is sufficient stock to remove the requested quantity
        if (this.currentStock.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock to remove the requested quantity.");
        }

        // Check if the removal will bring the stock below the minimum stock level
        if (this.minimumStock.getMinimumStock() >= this.currentStock.getStock() - quantity) {
            // TODO: Add event for generating an alert for low stock.
            this.addDomainEvent(new CreateLowStockAlertEvent(this, getId()));
        }

        // Removes the stock
        this.currentStock = this.currentStock.subtractStock(quantity);

        // If the stock reaches zero, set the product state to OUT_OF_STOCK
        if (this.currentStock.getStock() == 0) {
            this.productOutOfStock();
        }
    }

    /**
     * Sets the minimum stock for the product.
     * @param newMinimumStock The new minimum stock to be set for the product.
     */
    public void setMinimumStock(int newMinimumStock) {
        this.minimumStock = this.minimumStock.updateMinimumStock(newMinimumStock);
    }

    /**
     * Sets the product state to OUT_OF_STOCK.
     */
    public void productOutOfStock() {
        // If the product is already out of stock, throw an exception.
        if (this.productState == ProductState.OUT_OF_STOCK) {
            throw new IllegalStateException("Product is already out of stock.");
        }

        // If the product is not out of stock, set the product state to OUT_OF_STOCK.
        this.productState = ProductState.OUT_OF_STOCK;
    }

    /**
     * Sets the product state to WITH_STOCK.
     */
    public void productWithStock() {
        // If the product is already with stock, throw an exception.
        if (this.productState == ProductState.WITH_STOCK) {
            throw new IllegalStateException("Product is already with stock.");
        }

        // If the product is not out of stock, set the product state to WITH_STOCK.
        this.productState = ProductState.WITH_STOCK;
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
     * Changes the warehouse where the product is stored.
     * @param newWarehouse The new warehouse to which the product will be moved.
     */
    public void changeWarehouse(Warehouse newWarehouse) {
        if (newWarehouse == null) {
            throw new IllegalArgumentException("New warehouse cannot be null.");
        }
        this.warehouse = newWarehouse;
    }
}
