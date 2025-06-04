package com.stocksip.inventorymanagement.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

/**
 * Product Movement Aggregate
 *
 * @summary
 * The Product Movement class is an aggregate that represents a product movement in a warehouse.
 * It can be the result in a product consumption, donation, etc.
 * It is responsible for handling the CreateProductMovementCommand command.
 *
 * @since 1.0
 */
public class ProductMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    private Long productId;

    private int productAmount;

    private String movementReason;
}
