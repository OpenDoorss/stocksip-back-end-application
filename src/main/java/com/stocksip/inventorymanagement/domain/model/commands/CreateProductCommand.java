package com.stocksip.inventorymanagement.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

/**
 * CreateProductCommand
 *
 * @summary
 * CreateProductCommand is a record class that represents the command to register a product in a warehouse.
 *
 * @since 1.0.0
 */
public record CreateProductCommand(String name,
                                   String liquorType,
                                   String brandName,
                                   Double unitPriceAmount,
                                   Integer minimumStock,
                                   MultipartFile image,
                                   Long accountId) { }
