package com.stocksip.inventorymanagement.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event to create a low stock alert for a product.
 * This event is triggered when the stock of a product falls below the minimum stock set.
 */
@Getter
public class CreateLowStockAlertEvent extends ApplicationEvent {
    private final Long productId;

    public CreateLowStockAlertEvent(Object source, Long productId) {
        super(source);
        this.productId = productId;
    }
}
