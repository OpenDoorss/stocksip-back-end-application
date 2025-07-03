package com.stocksip.orderoperationandmonitoring.domain.services;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateOrderCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;
import java.util.Optional;

public interface PurchaseOrderCommandService {
    /**
     * Handles the creation of a new purchase order.
     *
     * @param cmd the command containing order details
     * @return an Optional containing the created PurchaseOrder, or empty if creation failed
     */
    Optional<PurchaseOrder> handle(CreateOrderCommand cmd);


    void changeStatus(Long orderId, OrderStatus newStatus);
}
