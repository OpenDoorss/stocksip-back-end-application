package com.stocksip.orderoperationandmonitoring.domain.model.commands;

public record PublishCatalogCommand(Long catalogId) {
    public PublishCatalogCommand {
        if (catalogId == null || catalogId <= 0)
            throw new IllegalArgumentException("catalogId must be positive");
    }
}
