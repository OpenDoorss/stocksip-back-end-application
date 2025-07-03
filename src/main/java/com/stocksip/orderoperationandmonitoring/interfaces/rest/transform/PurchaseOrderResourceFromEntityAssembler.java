package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.Buyer;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.OrderItem;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.Supplier;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.AccountResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.OrderItemResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.PurchaseOrderResource;

public class PurchaseOrderResourceFromEntityAssembler {

    public static PurchaseOrderResource toResource(PurchaseOrder po) {
        return new PurchaseOrderResource(
                po.getId(),
                po.getDate().value().toString(),
                po.getStatus().name(),
                toAccount(po.getBuyer()),
                toAccount(po.getSupplier()),
                po.getItems().stream().map(PurchaseOrderResourceFromEntityAssembler::toItem).toList(),
                po.getTotalAmount(),
                po.getTotalItems()
        );
    }

    private static AccountResource toAccount(Buyer b) {
        return new AccountResource(
                b.accountId(), b.userOwnerId(), b.role(), b.businessName(), b.email()
        );
    }
    private static AccountResource toAccount(Supplier s) {
        return new AccountResource(
                s.accountId(), s.userOwnerId(), s.role(), s.businessName(), s.email()
        );
    }

    private static OrderItemResource toItem(OrderItem i) {
        return new OrderItemResource(
                i.id().toString(),
                i.catalogId(),
                i.name(),
                i.productType(),
                i.brand(),
                i.content(),
                i.unitPrice(),
                i.dateAdded().toString(),
                i.customQuantity()
        );
    }
}
