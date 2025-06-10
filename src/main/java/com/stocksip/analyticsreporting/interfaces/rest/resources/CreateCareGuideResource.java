package com.stocksip.analyticsreporting.interfaces.rest.resources;

public record CreateCareGuideResource(String guideName,String type, String description) {
    public CreateCareGuideResource {
        if (guideName==null||guideName.isEmpty()||type == null || type.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Type and description must not be null or empty");
        }
    }
}
