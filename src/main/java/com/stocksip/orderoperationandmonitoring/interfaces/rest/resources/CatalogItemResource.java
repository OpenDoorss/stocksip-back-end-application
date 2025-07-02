package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CatalogItemResource(
        String      id,
        Long        catalogId,
        String      name,
        String      productType,
        String      brand,
        Integer     content,
        BigDecimal  unitPrice,
        LocalDateTime dateAdded
) {}

