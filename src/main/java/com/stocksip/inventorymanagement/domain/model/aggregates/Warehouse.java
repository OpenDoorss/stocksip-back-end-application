package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    /**
     * The unique identifier of the warehouse.
     * @type Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * The name of the warehouse
     * @type String
     */
    @Column(nullable = false)
    @Setter
    private String name;

    /**
     * The location where the warehouse is located. It can be within the liquor store or a location out of this place.
     * @type String
     */
    @Column(nullable = false)
    @Setter
    private String location;

    /**
     * The size in cubic meters of the warehouse. It's the physic space the warehouse uses.
     * @type double
     */
    @Column(nullable = false)
    private double warehouseSize;

    /**
     * The identifier of the user who owns this warehouse.
     * @type Long
     */
    @Column(nullable = false, updatable = false)
    @Getter
    private Long userId;

    protected Warehouse() {}

    /**
     * @summary Constructor.
     * This creates a new Warehouse instance based on the CreateWarehouseCommand command.
     * @param command - the CreateWarehouseCommand command
     */
    public Warehouse(CreateWarehouseCommand command) {
        this.id = command.id();
        this.name = command.name();
        this.location = command.location();
        this.warehouseSize = command.warehouseSize();
        this.userId = command.userId();
    }
}
