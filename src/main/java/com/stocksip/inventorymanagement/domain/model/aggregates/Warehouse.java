package com.stocksip.inventorymanagement.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Warehouse Aggregate
 *
 * @summary
 * The Warehouse class is an aggregate that represents an inventory in a liquor shop.
 * It is responsible for handling the CreateWarehouseCommand command.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
}
