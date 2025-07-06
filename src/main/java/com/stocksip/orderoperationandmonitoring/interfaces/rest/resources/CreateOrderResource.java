package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderResource(
        LocalDateTime orderDate,

        AccountResourceForOrders      buyer,
        AccountResourceForOrders      supplier,
        List<OrderItemResource> items,
        Double               totalAmount,
        Integer              totalItems
) {}
