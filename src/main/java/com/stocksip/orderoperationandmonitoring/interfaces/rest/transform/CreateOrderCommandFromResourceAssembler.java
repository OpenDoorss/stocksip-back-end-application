package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateOrderCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.Buyer;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.OrderItem;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.Supplier;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CreateOrderResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommand(CreateOrderResource r) {

        Buyer buyer = new Buyer(
                r.buyer().accountId(),
                r.buyer().userOwnerId(),
                r.buyer().role(),
                r.buyer().businessName(),
                r.buyer().email()
        );

        Supplier supplier = new Supplier(
                r.supplier().accountId(),
                r.supplier().userOwnerId(),
                r.supplier().role(),
                r.supplier().businessName(),
                r.supplier().email()
        );

        List<OrderItem> items = r.items().stream().map(it ->
                new OrderItem(
                        UUID.fromString(it.id()),
                        it.catalogId(),
                        it.name(),
                        it.productType(),
                        it.brand(),
                        it.content(),
                        it.unitPrice(),

                        it.dateAdded() != null
                                ? LocalDateTime.parse(it.dateAdded())
                                : LocalDateTime.now(),
                        it.customQuantity()
                )
        ).toList();

        return new CreateOrderCommand(
                buyer, supplier, items, r.totalAmount(), r.totalItems()
        );
    }
}