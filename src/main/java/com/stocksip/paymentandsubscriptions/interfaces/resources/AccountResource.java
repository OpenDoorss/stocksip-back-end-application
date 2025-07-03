package com.stocksip.paymentandsubscriptions.interfaces.resources;

public record AccountResource(
        Long accountId,
        String email,

        Long userOwnerId,

        String role,
        String businessName
) {}
