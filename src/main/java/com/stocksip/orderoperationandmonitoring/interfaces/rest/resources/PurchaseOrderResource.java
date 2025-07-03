package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

import java.util.List;

public record PurchaseOrderResource(
        Long id,
        String date,
        String status,
        AccountResource buyer,
        AccountResource supplier,
        List<OrderItemResource> items,
        Double totalAmount,
        Integer totalItems
) {}
