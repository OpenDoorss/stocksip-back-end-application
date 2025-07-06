package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;


import java.time.LocalDateTime;

public record CatalogResource(
        Long  id,
        Long accountId,
        String name,
        LocalDateTime dateCreated,
        boolean isPublished
) {}
