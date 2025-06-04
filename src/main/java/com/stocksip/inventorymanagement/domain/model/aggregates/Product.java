package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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
     * @type Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * The name of the product.
     * @type String
     */
    @Column(nullable = false)
    @Setter
    @Getter
    private String name;

    /**
     * The type of liquor the product is made of.
     * @type String
     */
    @Column(nullable = false)
    @Getter
    private String liquorType;

    /**
     * The date from which the product will no longer be consumable.
     * @type Date
     */
    @Column(nullable = false, updatable = false)
    private Date expirationDate;

    /**
     * The price the product can be sold to the customers of the liquor shop.
     * @type double
     */
    @Column(nullable = false)
    @Setter
    private double price;

    /**
     * The number of the current stock of the product in the warehouse.
     * @type int
     */
    @Column(nullable = false)
    @Setter
    private int currentStock;

    /**
     * The minimum number of products which can exist in a warehouse before generating an alert to the user.
     * It's used to generates alerts in the platform.
     * @type int
     */
    @Column(nullable = false)
    @Setter
    private int minimumStock;

    /**
     * The identifier of the warehouse where the product is stored.
     * @type Long
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private Long warehouseId;

    /**
     * The identifier of the user who provided this product to the liquor store owner.
     * @type Long
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private Long providerId;

    protected Product() {}

    /**
     * @summary Constructor.
     * This creates a new Product instance based on the CreateProductCommand command.
     * @param command - the CreateProductCommand command
     */
    public Product(CreateProductCommand command) {
        this.id = command.id();
        this.name = command.name();
        this.liquorType = command.liquorType();
        this.expirationDate = command.expirationDate();
        this.price = command.price();
        this.currentStock = command.currentStock();
        this.minimumStock = command.minimumStock();
        this.warehouseId = command.warehouseId();
        this.providerId = command.providerId();
    }
}
