package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

import java.util.List;

public record PurchaseOrderResource(
        Long id,
        String date,
        String status,
        AccountResourceForOrders buyer,
        AccountResourceForOrders supplier,
        List<OrderItemResource> items,
        Double totalAmount,
        Integer totalItems
) {}
