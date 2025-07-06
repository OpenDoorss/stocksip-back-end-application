package com.stocksip.inventorymanagement.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

/**
 * CreateProductResource is a record that represents an CreateProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an CreateProductCommand.
 *
 * @since 1.0.0
 */
public record CreateProductResource(
        String name,
        String liquorType,
        String brandName,
        Double unitPriceAmount,
        Integer minimumStock,
        MultipartFile image

) {
}
