package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.commands.*;

import java.util.Optional;

/**
 * ProductCommandService is an interface that defines methods for handling commands related to products.
 *
 * @summary
 * Service interface for handling commands related to products.
 *
 * @since 1.0.0
 */
public interface ProductCommandService {

    Optional<Product> handle(UpdateProductCommand command);

    Long handle(CreateProductCommand command);
    Long handle(UpdateProductMinimumStockCommand command);
}
