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
 * @since 1.0.1
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends AuditableAbstractAggregateRoot<Product> {

    /**
     * Unique identifier of the warehouse in which this product is stored.
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    /**
     * Unique identifier of the user that provided this product to the liquor store owner. It can be null.
     */
    @Getter
    @Embedded
    private ProviderId providerId;

    /**
     * Unique identifier of the catalog in which this product is shown. It can be null.
     */
    @Getter
    @Embedded
    private CatalogId catalogId;

    /**
     * Name the owner gives to this product. It can be null.
     */
    @Setter
    private String name;

    /**
     * Name of the brand to which this product belongs to.
     */
    @Column(nullable = false)
    private String brandName;

    /**
     * The liquor type of the product.
     */
    @Column(nullable = false)
    private LiquorType productType;

    /**
     * The current state of the product.
     */
    @Column(nullable = false)
    private ProductState productState;

    /**
     * The price which one product costs.
     */
    @Column(nullable = false)
    private double unitPrice;

    /**
     * The liquid content of this product. In milliliters.
     */
    @Column(nullable = false)
    private double productContent;

    /**
     * The current stock of the product. It can update to show the current stock to the liquor store owner.
     */
    @Column(nullable = false)
    private int currentStock;

    /**
     * A configuration which is used to set alarms when the currentStock reaches this amount.
     */
    @Setter
    @Column(nullable = false)
    private int minimumStock;

    /**
     * The url of the image that shows with the product.
     */
    @Column(nullable = false)
    private String imageUrl;

    protected Product() {
        // Default constructor for JPA.
    }

    /**
     * @summary Constructor.
     * It creates a new Product instance based on the CreateProductCommand command.
     *
     */
    public Product(Warehouse warehouse, ProviderId providerId, String imageUrl,
                   String name, String brandName, LiquorType productType, double unitPrice,
                   double productContent, int currentStock, int minimumStock) {

        this.warehouse = warehouse;
        this.providerId = providerId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.brandName = brandName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productContent = productContent;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        if (currentStock > 0) {
            this.productState = ProductState.WITH_STOCK;
        } else {
            this.productState = ProductState.OUT_OF_STOCK;
        }
        // this.addDomainEvent(new ProductRequestedEvent(this));
    }

    public Product(Warehouse warehouse, String imageUrl,
                   String name, String brandName, LiquorType productType, double unitPrice,
                   double productContent, int currentStock, int minimumStock) {

        this.warehouse = warehouse;
        this.providerId = null;
        this.catalogId = null;
        this.imageUrl = imageUrl;
        this.name = name;
        this.brandName = brandName;
        this.productType = productType;
        this.unitPrice = unitPrice;
        this.productContent = productContent;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        if (currentStock > 0) {
            this.productState = ProductState.WITH_STOCK;
        } else {
            this.productState = ProductState.OUT_OF_STOCK;
        }
        // this.addDomainEvent(new ProductRequestedEvent(this));
    }

    /**
     * Method to get the current state of the product.
     * @return It returns the current state of the product.
     */
    public String getStatus() {
        return this.productState.name().toLowerCase();
    }

    /**
     * Method to update the product information.
     * @param imageUrl The new image url of the product.
     * @param name The new name of the product.
     * @param brandName The new brand name of the product.
     * @param productType The new liquor type of the product.
     * @param unitPrice The new unit price of the product.
     * @param productContent The new liquid amount of content of the product.
     * @return The updated product instance.
     */
    public Product updateInformation(String imageUrl, String name, String brandName, LiquorType productType,
                                     double unitPrice, double productContent) {
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
        if (name != null) {
            this.name = name;
        }
        if (brandName != null) {
            this.brandName = brandName;
        }
        if (productType != null) {
            this.productType = productType;
        }
        if (unitPrice >= 0) {
            this.unitPrice = unitPrice;
        }
        if (productContent >= 0) {
            this.productContent = productContent;
        }

        return this;
    }

    /**
     * Method to get the complete name of the product.
     * Example 1: Whisky JOHNNIE WALKER Blue Label.
     * Example 2: Cerveza Cristal.
     * @return The full name of the product.
     */
    public String getProductFullName() {
        if (name != null)
        {
            return this.productType + " " + this.brandName + " " + this.name;
        } else {
            return this.productType + " " + this.brandName;
        }

    }

    /**
     * Method to add stock to the product.
     * It assures the input quantity is always a positive integer number.
     * It also changes the product state to WITH STOCK.
     * @param quantity The stock of this product to add.
     */
    public void addStock(int quantity) {
        if (quantity > 0) {
            this.currentStock += quantity;
            this.productState = ProductState.WITH_STOCK;
        }
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    /**
     * Method to remove stock of this product.
     * It assures that the input quantity is always a positive integer number and is equal or less than the current stock.
     * It also changes the product state to OUT OF STOCK if the resulting number of the stock of this product is zero.
     * @param quantity The stock of this product to remove.
     */
    public void removeStock(int quantity) {
        if (quantity > 0 && quantity <= this.currentStock) {
            this.currentStock -= quantity;
        }

        if (this.currentStock == 0) {
            this.productState = ProductState.OUT_OF_STOCK;
        }
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

    /**
     * Method when the product is added to a catalog.
     * @param catalogId The Identifier of the catalog in which this product will be shown.
     */
    public void addProductToCatalog(CatalogId catalogId) {
        if (this.catalogId == null && !(catalogId == null)) {
            this.catalogId = catalogId;
        }
        // this.addDomainEvent(new ProductConfirmedEvent(this));
    }

}
