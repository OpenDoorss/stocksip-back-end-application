package com.stocksip.orderoperationandmonitoring.interfaces.rest.resources;

public record OrderItemResource(
        String id,
        Long catalogId,
        String name,
        String productType,
        String brand,
        Integer content,
        Double unitPrice,
        String  dateAdded,
        Integer customQuantity
) {}