package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * Represents the stock of a product in the inventory management system.
 * This value object encapsulates the stock quantity and provides methods to manipulate it.
 *
 * @param stock The stock of the product must be a non-negative integer.
 */
@Embeddable
public record ProductStock(Integer stock) {

    /**
     * This constructor validates the input parameter to ensure that it is a non-negative integer.
     *
     * @param stock The stock of the product.
     * @throws IllegalArgumentException if the stock is negative.
     */
    public ProductStock {
        if (!(isStockValidate(stock))) {
            throw new IllegalArgumentException("Stock must be a non-negative integer.");
        }

    }

    /**
     * Validates the stock.
     *
     * @param stock The stock of the product.
     * @return true if the stock is a positive integer number, false otherwise.
     */
    private static boolean isStockValidate(Integer stock) {
        return !(stock < 0);
    }

    /**
     * Updates the stock of the product.
     * @param addedStock The amount of stock to be added to the current stock.
     * @return A new instance of ProductStock with the updated stock.
     */
    public ProductStock addStock(Integer addedStock) {
        if (!(isStockValidate(addedStock))) {
            throw new IllegalArgumentException("Added stock must be a positive integer.");
        }
        return new ProductStock(this.stock + addedStock);
    }

    /**
     * Subtracts stock from the current stock of the product.
     * @param subtractedStock The amount of stock to be subtracted from the current stock.
     * @return A new instance of ProductStock with the updated stock.
     */
    public ProductStock subtractStock(Integer subtractedStock) {
        if (subtractedStock <= 0) {
            throw new IllegalArgumentException("Subtracted stock must be a positive integer.");
        }
        if (this.stock < subtractedStock) {
            throw new IllegalArgumentException("Subtracted to decrease by the specified quantity.");
        }
        return new ProductStock(this.stock - subtractedStock);
    }

    /**
     * Gets the current stock of the product.
     *
     * @return The stock of the product.
     */
    public int getStock() {
        return stock;
    }
}
