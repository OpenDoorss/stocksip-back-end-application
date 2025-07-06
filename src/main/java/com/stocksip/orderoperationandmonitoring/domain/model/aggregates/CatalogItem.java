package com.stocksip.orderoperationandmonitoring.domain.model.aggregates;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateCatalogItemCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class CatalogItem {
    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", nullable = false)
    @Setter
    private Catalog catalog;


    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "name", nullable = false, length = 100))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "product_type", nullable = false, length = 50))
    private ProductType productType;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "brand", nullable = false, length = 50))
    private BrandName brand;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "content", nullable = false))
    private ContentVolume content;


    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;


    protected CatalogItem() {}  // JPA

    public CatalogItem(CreateCatalogItemCommand cmd) {
        this.id          = UUID.randomUUID().toString();
        this.name        = new ProductName(cmd.name());
        this.productType = new ProductType(cmd.productType());
        this.brand       = new BrandName(cmd.brand());
        this.content     = new ContentVolume(cmd.content());
        this.unitPrice   = cmd.unitPrice();
        this.dateAdded   = LocalDateTime.now();
    }
}
