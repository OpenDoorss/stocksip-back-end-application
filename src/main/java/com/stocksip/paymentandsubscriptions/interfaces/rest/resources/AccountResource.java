package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

public record AccountResource(
        Long accountId,
        String username,
        String password,
        String status,
        String businessName,
        String role
) {}
