package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.entities.ProductExit;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductExitReasons;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductState;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductStock;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

/**
 * Represents an inventory item in the inventory management system.
 * This entity encapsulates the relationship between a product and a warehouse,
 * along with its stock and state.
 *
 * @since 1.0.0
 */
@Entity
@Getter
public class Inventory extends AuditableAbstractAggregateRoot<Inventory> {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    /**
     * The product associated with this inventory item.
     * It is a mandatory field and cannot be null.
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    /**
     * The warehouse where this inventory item is stored.
     * It is a mandatory field and cannot be null.
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouseId", nullable = false)
    private Warehouse warehouse;

    /**
     * The stock of the product in this inventory item.
     * It is an embedded value object that contains the stock quantity.
     */
    @Embedded
    private ProductStock productStock;

    /**
     * The state of the product in this inventory item.
     * It indicates whether the product is currently in stock or out of stock.
     */
    private ProductState productState;

    /**
     * The best before date of the product in this inventory item.
     * It is an embedded value object that contains the date.
     */
    @Embedded
    private ProductBestBeforeDate productBestBeforeDate;

    /**
     * The list of product exits associated with this inventory item.
     * It represents the history of exits for this product in the warehouse.
     */
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    @Getter
    private List<ProductExit> productExits;

    protected Inventory() {
        // Default constructor for JPA
    }

    /**
     * Constructor to create a new Inventory instance.
     *
     * @param product The product associated with this inventory item.
     * @param warehouse The warehouse where this inventory item is stored.
     * @param productStock The stock of the product in this inventory item.
     * @param productBestBeforeDate The best before date of the product in this inventory item.
     */
    public Inventory(Product product, Warehouse warehouse, ProductStock productStock, ProductBestBeforeDate productBestBeforeDate) {
        this.product = product;
        this.warehouse = warehouse;
        this.productStock = productStock;
        this.productBestBeforeDate = productBestBeforeDate;
        this.productState = ProductState.WITH_STOCK;
    }

    /**
     * Updates the product state based on the current stock.
     * If the stock is zero, the product state is set to OUT_OF_STOCK.
     * If the stock is greater than zero, the product state is set to WITH_STOCK.
     */
    private void setProductStateToOutOfStock() {
        // Ensure that the product is not already out of stock before changing its state
        if (productState == ProductState.OUT_OF_STOCK) {
            throw new IllegalArgumentException("Product is already out of stock.");
        }

        // Change the product state to OUT_OF_STOCK
        this.productState = ProductState.OUT_OF_STOCK;
    }

    /**
     * Sets the product state to WITH_STOCK.
     * This method is called when stock is added to the product.
     * It ensures that the product is not already in stock before changing its state.
     */
    private void setProductStateToWithStock() {
        // Ensure that the product is not already in stock before changing its state
        if (productState == ProductState.WITH_STOCK) {
            throw new IllegalArgumentException("Product is already in stock.");
        }

        // Change the product state to WITH_STOCK
        this.productState = ProductState.WITH_STOCK;
    }

    /**
     * Adds stock to the product in this inventory item.
     * If the product is currently out of stock, it changes its state to WITH_STOCK.
     *
     * @param stockToAdd The quantity of stock to be added to the product.
     */
    public void addStockToProduct(int stockToAdd) {
        // Validate the stock to add
        if (stockToAdd <= 0) {
            throw new IllegalArgumentException("Stock to add must be a positive number.");
        }

        // If the product is currently out of stock, change its state to WITH_STOCK
        if (productStock.getStock() == 0) {
            setProductStateToOutOfStock();
        }

        // Update the product stock
        this.productStock = this.productStock.addStock(stockToAdd);
    }

    /**
     * Reduce stock from the product in this inventory item.
     * If the stock of the product is below the minimum stock level, an alert will be generated.
     * If the stock of the product is zero after the reduction, the state of the product will be set as OUT_OF_STOCK and a
     * critical alert will be generated.
     *
     * @param stockToReduce The quantity of stock to be reduced from the product.
     */
    public void reduceStockFromProduct(int stockToReduce) {
        // Validate the stock to reduce
        if (stockToReduce <= 0) {
            throw new IllegalArgumentException("Stock to reduce must be a positive number.");
        }

        // If the product is currently in stock, change its state to OUT_OF_STOCK if stock becomes zero
        if (productStock.getStock() < stockToReduce) {
            throw new IllegalArgumentException("Insufficient stock to reduce the requested quantity.");
        }

        // If the stock after reduction is below the minimum stock level, then an alert should be generated
        if (product.getMinimumStock().getMinimumStock() >= productStock.getStock() - stockToReduce) {
            //TODO: Add event for generating a warning alert for product with low stock.

        }

        // Reduce the stock
        productStock = productStock.subtractStock(stockToReduce);

        // If the stock reaches zero, set the product state to OUT_OF_STOCK and generate a critical alert.
        if (productStock.getStock() == 0) {
            setProductStateToOutOfStock();
            //TODO: Add event for generating a critical alert for product with empty stock.
        }
    }

    /**
     * Registers an exit of stock from the inventory.
     * This method creates a new ProductExit instance and adds it to the inventory's product exits.
     *
     * @param stockExited The quantity of stock that has exited the inventory.
     * @param exitReason The reason for the exit, represented as a string.
     */
    public void registerExit(int stockExited, ProductExitReasons exitReason) {

        // Validate the stock exited
        if (stockExited <= 0) {
            throw new IllegalArgumentException("Stock exited must be a positive number.");
        }

        // Reduce the stock from the product in this inventory item.
        this.reduceStockFromProduct(stockExited);

        // Create a new ProductExit instance with the provided exit reason and stock exited
        var productExit = new ProductExit(this, exitReason, stockExited);

        // Add the product exit to the inventory
        this.productExits.add(productExit);
    }
}
