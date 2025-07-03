package com.stocksip.orderoperationandmonitoring.domain.model.commands;

import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.*;

import java.util.List;

public record CreateOrderCommand(
        Buyer buyer,
        Supplier supplier,
        List<OrderItem> items,
        Double totalAmount,
        Integer totalItems
) {}
