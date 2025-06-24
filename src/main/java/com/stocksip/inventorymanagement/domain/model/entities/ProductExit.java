package com.stocksip.inventorymanagement.domain.model.entities;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductExitReasons;
import com.stocksip.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/**
 * Product Exit Entity
 *
 * @summary
 * The Product Exit class is an entity that represents a product exit in a warehouse.
 * It can be the result in a product consumption, donation, expiration, etc.
 * It is responsible for handling the CreateProductExitCommand command.
 *
 * @since 1.0.0
 */
@Entity
public class ProductExit extends AuditableModel {

    /**
     * The unique identifier for the inventory.
     */
    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "inventory_id", nullable = false, updatable = false)
    private Inventory inventory;

    /**
     * The reason why the product is exiting the warehouse.
     */
    @Getter
    @Column(nullable = false, updatable = false)
    private ProductExitReasons exitReason;

    /**
     * The number of products which will exit the warehouse.
     */
    @Column(nullable = false, updatable = false)
    private int productAmountExited;

    /**
     * The date which this exit is being registered.
     */
    @Column(nullable = false, updatable = false)
    @Getter
    @CreatedDate
    private Date exitDate;

    protected ProductExit() {
        // Default constructor for JPA.
    }

    /**
     * @summary
     * Default Constructor.
     * Creates a new ProductExit instance.
     */
    public ProductExit(Inventory inventory, ProductExitReasons exitReason, int productAmount) {
        this.inventory = inventory;
        this.exitReason = exitReason;
        this.productAmountExited = productAmount;
    }

    /**
     * Method to get the reason of the exit of these products.
     *
     * @return The reason of exiting of these products.
     */
    public String getExitReason() {
        return this.exitReason.name().toLowerCase();
    }
}
