package com.stocksip.inventorymanagement.domain.model.commands;

import com.stocksip.inventorymanagement.domain.model.valueobjects.ProviderId;

import java.util.Date;
import java.util.Optional;

/**
 * CreateProductCommand
 *
 * @summary
 * CreateProductCommand is a record class that represents the command to register a product in a warehouse.
 *
 * @since 1.0.0
 */
public record CreateProductCommand(String additionalName,
                                   String liquorType,
                                   String brandName,
                                   double unitPriceAmount,
                                   int minimumStock,
                                   String imageUrl,
                                   ProviderId providerId) { }
