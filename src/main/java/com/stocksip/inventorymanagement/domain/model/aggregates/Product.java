package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.entities.Brand;
import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.*;
import com.stocksip.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Currency;
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
public class Product extends AbstractAggregateRoot<Product> {

    /**
     * The unique identifier of the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long productId;

    /**
     * The type of liquor the product is made of.
     */
    @Column(nullable = false)
    @Getter
    private LiquorType liquorType;

    /**
     * The full name of the product.
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private ProductName name;

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
     * The brand of the product.
     * This is a many-to-one relationship with the Brand entity.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    /**
     * The identifier of the user who provided this product to the liquor store owner.
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private ProviderId providerId;

    protected Product() {
        // Default constructor for JPA
    }

    public Product(Warehouse warehouse, ProviderId providerId, Brand brand, String liquorType,
                   Optional<String> additionalName, Date expirationDate, double price, int currentStock,
                   int minimumStock, String imageUrl) {
        this.name = new ProductName(brand.getName(), LiquorType.valueOf(liquorType.toUpperCase()), additionalName);
        this.expirationDate = expirationDate;
        this.minimumStock = new ProductMinimumStock(minimumStock);
        this.currentStock = new ProductStock(currentStock);
        this.unitPrice = new Money(price, "PEN");
        this.liquorType = LiquorType.valueOf(liquorType.toUpperCase());

        this.warehouse = warehouse;
        this.providerId = providerId;
        this.brand = brand;
        this.imageUrl = setDefaultImageUrlIfNotProvided(imageUrl);
    }

    private ImageUrl setDefaultImageUrlIfNotProvided(String imageUrl) {
        return imageUrl == null || imageUrl.isBlank()
                ? ImageUrl.defaultImageUrl()
                : new ImageUrl(imageUrl);
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.currentStock = this.currentStock.addStock(quantity);
    }

    public void removeStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (this.currentStock.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock to remove the requested quantity.");
        }
        this.currentStock = this.currentStock.subtractStock(quantity);
    }
}
