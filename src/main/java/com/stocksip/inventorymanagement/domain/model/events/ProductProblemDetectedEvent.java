package com.stocksip.inventorymanagement.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductProblemDetectedEvent extends ApplicationEvent {
    private final String title;
    private final String message;
    private final String severity;
    private final String type;
    private final String accountId;
    private final String productId;
    private final String warehouseId;

    public ProductProblemDetectedEvent(Object source, String title, String message, String severity, String type, String accountId, String productId, String warehouseId) {
        super(source);
        this.title = title;
        this.message = message;
        this.severity = severity;
        this.type = type;
        this.accountId = accountId;
        this.productId = productId;
        this.warehouseId = warehouseId;
    }

}