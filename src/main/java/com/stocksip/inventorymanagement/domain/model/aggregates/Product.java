package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.valueobjects.CatalogId;
import com.stocksip.inventorymanagement.domain.model.valueobjects.LiquorType;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductState;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProviderId;
import com.stocksip.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Getter
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Getter
    @Embedded
    private ProviderId providerId;

    @Getter
    @Embedded
    private CatalogId catalogId;

    @Setter
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LiquorType productType;

    @Column(nullable = false)
    private ProductState productState;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double productContent;

    @Column(nullable = false)
    private int currentStock;

    @Column(nullable = false)
    private int minimumStock;

    @Column(nullable = false)
    private String imageUrl;

    protected Product() {
        // Default constructor for JPA.
    }

    public Product(Warehouse warehouse, ProviderId providerId, String imageUrl,
                   String name, LiquorType productType, double unitPrice,
                   double productContent, int currentStock, int minimumStock) {

        this.warehouse = warehouse;
        this.providerId = providerId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productContent = productContent;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.productState = ProductState.IN_WAREHOUSE;
        // this.addDomainEvent(new ProductRequestedEvent(this));
    }

    public Product(Warehouse warehouse, String imageUrl,
                   String name, LiquorType productType, double unitPrice,
                   double productContent, int currentStock, int minimumStock) {

        this.warehouse = warehouse;
        this.providerId = null;
        this.catalogId = null;
        this.imageUrl = imageUrl;
        this.name = name;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productContent = productContent;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.productState = ProductState.IN_WAREHOUSE;
        // this.addDomainEvent(new ProductRequestedEvent(this));
    }

    public void loss() {
        this.productState = ProductState.LOST;
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    public void sell() {
        this.productState = ProductState.SOLD;
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    public void donate() {
        this.productState = ProductState.DONATED;
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    public void consume() {
        this.productState = ProductState.CONSUMED;
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    public String getStatus() {
        return this.productState.name().toLowerCase();
    }

    public boolean isLost() {
        return ProductState.LOST.equals(this.productState);
    }

    public boolean isSold() {
        return  ProductState.SOLD.equals(this.productState);
    }

    public boolean isConsumed() {
        return ProductState.CONSUMED.equals(this.productState);
    }

    public boolean isDonated() {
        return ProductState.DONATED.equals(this.productState);
    }

    public boolean isInWarehouse() {
        return ProductState.IN_WAREHOUSE.equals(this.productState);
    }
}
