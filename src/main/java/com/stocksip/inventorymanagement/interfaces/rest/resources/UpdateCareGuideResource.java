package com.stocksip.inventorymanagement.interfaces.rest.resources;

public record UpdateCareGuideResource(
    String guideName,
    String type,
    String description,
    String imageUrl
) {
}
