package com.stocksip.orderoperationandmonitoring.domain.model.commands;

import java.math.BigDecimal;

public record CreateCatalogItemCommand(
        Long catalogId,
        String name,
        String productType,
        String brand,
        Integer content,
        BigDecimal unitPrice
) {}