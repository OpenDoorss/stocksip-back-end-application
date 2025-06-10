package com.stocksip.inventorymanagement.domain.model.aggregates;

import com.stocksip.inventorymanagement.domain.model.entities.Brand;
import com.stocksip.inventorymanagement.domain.model.entities.ProductType;
import com.stocksip.inventorymanagement.domain.model.entities.ProductZone;
import com.stocksip.inventorymanagement.domain.model.valueobjects.LiquorType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Product Aggregate Root
 *
 * @summary
 * The Product class is an aggregate root that represents a product in a warehouse.
 * It is responsible for handling the CreateProductCommand command.
 *
 * @since 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends AbstractAggregateRoot<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long productId;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductZone> productZones = new ArrayList<>();

    protected Product() {}

}
