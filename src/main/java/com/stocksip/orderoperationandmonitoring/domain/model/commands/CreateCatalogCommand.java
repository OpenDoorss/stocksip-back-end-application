package com.stocksip.orderoperationandmonitoring.domain.model.commands;

public record CreateCatalogCommand(Long accountId, String name) {

    public CreateCatalogCommand {
        if (accountId <= 0)
            throw new IllegalArgumentException("accountId is required");

        if (name == null)
            throw new IllegalArgumentException("name is required");
    }
}
