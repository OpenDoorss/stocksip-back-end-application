package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record OrderItem(
        java.util.UUID id, Long catalogId,
        String name,
        String productType,
        String brand,
        Integer content,
        Double unitPrice,
        LocalDateTime dateAdded,
        Integer customQuantity
) {

}
