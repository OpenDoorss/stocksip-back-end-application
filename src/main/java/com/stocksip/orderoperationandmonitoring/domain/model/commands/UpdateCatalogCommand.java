package com.stocksip.orderoperationandmonitoring.domain.model.commands;


public record UpdateCatalogCommand(Long catalogId, Long accountId, String name) {

    public UpdateCatalogCommand {
        if (name == null )
            throw new IllegalArgumentException("name is required");
    }

}