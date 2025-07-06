package com.stocksip.inventorymanagement.domain.model.entities;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductExitReasons;
import com.stocksip.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
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
     * The identifier of the product that will exit.
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

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
    private int productAmount;

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
     * @summary Constructor.
     * It creates a new ProductExit instance based on the CreateProductExitCommand command.
     *
     */
    public ProductExit(Product product, ProductExitReasons exitReason, int productAmount) {
        this.product = product;
        this.exitReason = exitReason;
        this.productAmount = productAmount;

        //TODO: Add command for removing stock of a product with its id.
    }

    /**
     * Method to get the reason of the exit of these products.
     *
     * @return The reason of exiting of these products.
     */
    public String getReason() {
        return this.exitReason.name().toLowerCase();
    }
}
