package com.stocksip.orderoperationandmonitoring.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class PurchaseOrderItem {
    /**
     * @type Long
     * The unique identifier for the purchase order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @type String
     * The name of the purchase order item.
     */
    private String name;

    /**
     * @type Long
     * The identifier of the purchase order to which this item belongs.
     */
    private Long purchaseOrderId;

}
