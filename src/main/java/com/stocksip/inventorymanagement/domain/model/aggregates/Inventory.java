package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.events.ProductProblemDetectedEvent;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductBestBeforeDate;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductState;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProductStock;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    private ProductState productState;

    /**
     * The best before date of the product in this inventory item.
     * It is an embedded value object that contains the date.
     */
    @Embedded
    private ProductBestBeforeDate productBestBeforeDate;

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
    public Inventory(Product product, Warehouse warehouse, Integer productStock, LocalDate productBestBeforeDate) {
        this.product = product;
        this.warehouse = warehouse;
        this.productStock = new ProductStock(productStock);
        this.productBestBeforeDate = new ProductBestBeforeDate(productBestBeforeDate);
        this.productState = ProductState.With_Stock;
    }

    /**
     * Updates the product state based on the current stock.
     * If the stock is zero, the product state is set to OUT_OF_STOCK.
     * If the stock is greater than zero, the product state is set to WITH_STOCK.
     */
    private void setProductStateToOutOfStock() {
        // Ensure that the product is not already out of stock before changing its state
        if (productState == ProductState.Out_Of_Stock) {
            throw new IllegalArgumentException("Product is already out of stock.");
        }

        // Change the product state to OUT_OF_STOCK
        this.productState = ProductState.Out_Of_Stock;
    }

    /**
     * Sets the product state to WITH_STOCK.
     * This method is called when stock is added to the product.
     * It ensures that the product is not already in stock before changing its state.
     */
    private void setProductStateToWithStock() {
        // Ensure that the product is not already in stock before changing its state
        if (productState == ProductState.With_Stock) {
            throw new IllegalArgumentException("Product is already in stock.");
        }

        // Change the product state to WITH_STOCK
        this.productState = ProductState.With_Stock;
    }

    /**
     * This method updates the best before date of the product in this inventory item.
     * @param newBestBeforeDate - The new best before date to be set for the product.
     */
    public void updatedBestBeforeDate(LocalDate newBestBeforeDate) {

        // Update the product's best before date
        this.productBestBeforeDate = new ProductBestBeforeDate(newBestBeforeDate);
    }

    /**
     * Adds stock to the product in this inventory item.
     * If the product is currently out of stock, it changes its state to WITH_STOCK.
     *
     * @param stockToAdd The quantity of stock to be added to the product.
     */
    public void addStockToProduct(Integer stockToAdd) {
        // Validate the stock to add
        if (stockToAdd <= 0) {
            throw new IllegalArgumentException("Stock to add must be a positive number.");
        }

        // Update the product stock
        this.productStock = this.productStock.addStock(stockToAdd);

        // If the product is currently out of stock, change its state to WITH_STOCK
        if (productStock.getStock() > 0) {
            setProductStateToWithStock();
        }
    }

    /**
     * Reduce stock from the product in this inventory item.
     * If the stock of the product is below the minimum stock level, an alert will be generated.
     * If the stock of the product is zero after the reduction, the state of the product will be set as OUT_OF_STOCK and a
     * critical alert will be generated.
     *
     * @param stockToReduce The quantity of stock to be reduced from the product.
     */
    public void reduceStockFromProduct(Integer stockToReduce) {
        // Validate the stock to reduce
        if (stockToReduce <= 0) {
            throw new IllegalArgumentException("Stock to reduce must be a positive number.");
        }

        // If the product is currently in stock, change its state to OUT_OF_STOCK if the stock becomes zero
        if (productStock.getStock() < stockToReduce) {
            throw new IllegalArgumentException("Insufficient stock to reduce the requested quantity.");
        }

        // If the stock after reduction is below the minimum stock level, then an alert should be generated
        if (product.getMinimumStock().getMinimumStock() >= productStock.getStock() - stockToReduce) {
            addDomainEvent( new ProductProblemDetectedEvent(
                    this,
                    "Product Stock Alert",
                    String.format("The stock of product %s in warehouse %s is below the minimum threshold.", 
                        product.getProductId(), warehouse.getWarehouseId()),
                    "Warning",
                    "ProductLowStock",
                    warehouse.getAccountId().accountId().toString(),
                    product.getProductId().toString(),
                    warehouse.getWarehouseId().toString()
                )
            );
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
     * Checks if the product is approaching its expiration date and generates an alert if necessary.
     * The alert severity is determined based on the number of days until expiration:
     * - WARNING: 3 days or less (critical - immediate action required)
     * - HIGH: 4-7 days (high priority - urgent attention needed)
     * - MEDIUM: 8-14 days (moderate - plan action)
     * - LOW: 15-30 days (low - informational)
     *
     * @return ProductProblemDetectedEvent if an alert should be generated, otherwise null
     */
    public ProductProblemDetectedEvent checkExpirationWarning() {
        LocalDate currentDate = LocalDate.now();
        LocalDate bestBeforeDate = productBestBeforeDate.bestBeforeDate();
        // Only check if the product has not expired yet
        if (currentDate.isBefore(bestBeforeDate)) {
            long daysUntilExpiration = currentDate.until(bestBeforeDate).getDays();
            String severity = determineExpirationSeverity(daysUntilExpiration);
            // Generate alert only if the product falls within a warning range
            if (severity != null) {
                return new ProductProblemDetectedEvent(
                        this,
                    "Product Expiration Warning",
                    String.format("The product %s in warehouse %s expires in %d days.",
                        product.getProductId(),
                        warehouse.getWarehouseId(),
                        daysUntilExpiration),
                    severity,
                    "EXPIRATION_WARNING",
                    warehouse.getAccountId().accountId().toString(),
                    product.getProductId().toString(),
                    warehouse.getWarehouseId().toString()
                );
            }
        }
        return null;
    }

    /**
     * Determines the severity level of an expiration warning based on the number of days until expiration.
     * 
     * @param daysUntilExpiration The number of days until the product expires
     * @return The severity level as a string, or null if no warning should be generated
     */
    private String determineExpirationSeverity(long daysUntilExpiration) {
        if (daysUntilExpiration <= 3) {
            return "WARNING";  // Critical - immediate action required
        } else if (daysUntilExpiration <= 7) {
            return "HIGH";     // High priority - urgent attention needed
        } else if (daysUntilExpiration <= 14) {
            return "MEDIUM";   // Moderate - plan action
        } else if (daysUntilExpiration <= 30) {
            return "LOW";      // Low - informational
        }
        return null; // No warning generated if outside the warning ranges
    }
}
