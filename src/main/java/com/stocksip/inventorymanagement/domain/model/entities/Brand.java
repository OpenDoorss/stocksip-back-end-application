package com.stocksip.inventorymanagement.domain.model.entities;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.valueobjects.BrandName;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Brand Entity
 *
 * @summary
 * The Brand class represents a brand of a product in the inventory management system.
 *
 * @since 1.0.0
 */
@Entity
@Getter
public class Brand {

    /**
     * The unique identifier of the brand.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    /**
     * The name of the brand.
     */
    @Enumerated(EnumType.STRING)
    private BrandName name;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();
}
