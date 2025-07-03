package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * This record represents a resource for product inventory details.
 * @param productId - The unique identifier of the product.
 * @param name - The name of the product.
 * @param brand - The brand of the product.
 * @param unitPriceAmount - The unit price amount of the product.
 * @param minimumStock - The minimum stock level for the product.
 * @param currentStock - The current stock level of the product.
 * @param status - The status of the product (e.g., available, out of stock).
 * @param bestBeforeDate - The best before date of the product, indicating until when it retains its properties.
 */
public record ProductInventoryResource(Long productId,
                                       String name,
                                       String brand,
                                       Double unitPriceAmount,
                                       Integer minimumStock,
                                       Integer currentStock,
                                       String status,
                                       LocalDate bestBeforeDate) {}
