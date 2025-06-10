package com.stocksip.inventorymanagement.domain.model.entities;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Zone;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProductZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private int stock;

    protected ProductZone() {}
}
