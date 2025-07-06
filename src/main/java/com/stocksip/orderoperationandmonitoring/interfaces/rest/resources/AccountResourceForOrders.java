package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

public record AccountResourceForOrders(
        Long accountId,
        Long userOwnerId,
        String role,
        String businessName,
        String email
) {}