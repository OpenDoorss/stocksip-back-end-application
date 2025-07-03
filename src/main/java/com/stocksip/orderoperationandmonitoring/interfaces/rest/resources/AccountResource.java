package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

public record AccountResource(
        Long accountId,
        Long userOwnerId,
        String role,
        String businessName,
        String email
) {}