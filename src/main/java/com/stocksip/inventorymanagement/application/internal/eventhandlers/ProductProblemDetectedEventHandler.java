package com.stocksip.inventorymanagement.application.internal.eventhandlers;

import com.stocksip.inventorymanagement.domain.model.events.ProductProblemDetectedEvent;
import com.stocksip.inventorymanagement.application.acl.ExternalAlertsAndNotificationsService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for ProductProblemDetectedEvent.
 * Listens for product problem detection events in the inventory context and triggers the creation
 * of an alert using the external alerts and notifications service.
 *
 * @since 1.0
 */
@Component
public class ProductProblemDetectedEventHandler {

    private final ExternalAlertsAndNotificationsService alertsAndNotificationsService;

    /**
     * Constructor for ProductProblemDetectedEventHandler.
     *
     * @param alertsAndNotificationsService The service responsible for creating alerts in other contexts.
     */
    public ProductProblemDetectedEventHandler(ExternalAlertsAndNotificationsService alertsAndNotificationsService) {
        this.alertsAndNotificationsService = alertsAndNotificationsService;
    }

    /**
     * Handles the ProductProblemDetectedEvent.
     * This method is automatically invoked when a ProductProblemDetectedEvent is published.
     * It creates an alert using the event details.
     *
     * @param event The event containing the details of the detected product problem.
     */
    @EventListener
    public void handle(ProductProblemDetectedEvent event) {
        try {
            alertsAndNotificationsService.createAlert(
                event.getTitle(),
                event.getMessage(),
                event.getSeverity(),
                event.getType(),
                event.getAccountId(),
                event.getProductId(),
                event.getWarehouseId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 