package com.stocksip.inventorymanagement.domain.model.valueobjects;

public record ProductStock(int stock) {

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
    private static boolean isStockValidate(int stock) {
        return !(stock < 0);
    }

    /**
     * Updates the stock of the product.
     * @param addedStock The amount of stock to be added to the current stock.
     * @return A new instance of ProductStock with the updated stock.
     */
    public ProductStock addStock(int addedStock) {
        if (!(isStockValidate(addedStock))) {
            throw new IllegalArgumentException("Added stock must be a non-negative integer.");
        }
        return new ProductStock(this.stock + addedStock);
    }

    /**
     * Subtracts stock from the current stock of the product.
     * @param subtractedStock The amount of stock to be subtracted from the current stock.
     * @return A new instance of ProductStock with the updated stock.
     */
    public ProductStock subtractStock(int subtractedStock) {
        if (!(isStockValidate(subtractedStock))) {
            throw new IllegalArgumentException("Subtracted stock must be a non-negative integer.");
        }
        if (this.stock - subtractedStock < 0) {
            throw new IllegalArgumentException("Subtracted stock cannot exceed current stock.");
        }
        return new ProductStock(this.stock - subtractedStock);
    }
}
