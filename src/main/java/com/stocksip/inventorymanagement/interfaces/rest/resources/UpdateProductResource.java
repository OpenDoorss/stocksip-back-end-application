package com.stocksip.inventorymanagement.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

/**
 * UpdateProductResource is a record that represents an UpdateProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an UpdateProductCommand.
 *
 * @since 1.0.0
 */
public record UpdateProductResource(
        String name,
        String brand,
        String liquorType,
        Double updatedUnitPriceAmount,
        Integer updatedMinimumStock,
        MultipartFile updatedImage
) {
}
