package com.stocksip.orderoperationandmonitoring.domain.model.aggregates;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateCatalogCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.UpdateCatalogCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.*;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id")
    private Long catalogId;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "account_id", nullable = false))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "catalog_name", nullable = false, length = 100))
    private CatalogName name;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "date_created", nullable = false))
    private DateCreated dateCreated;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished;

    @OneToMany(
            mappedBy = "catalog",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CatalogItem> items = new ArrayList<>();

    protected Catalog() {}

    public Catalog(CreateCatalogCommand command) {
        this.accountId   = new AccountId(command.accountId());
        this.name        = new CatalogName(command.name());
        this.dateCreated = new DateCreated(LocalDateTime.now());
        this.isPublished = false;
    }

    public Catalog update(UpdateCatalogCommand command) {
        this.name = new CatalogName(command.name());
        return this;
    }


    public void publish() {
        if (this.isPublished)
            throw new IllegalStateException("Catalog already published");

        if (items == null || items.isEmpty())
            throw new IllegalStateException("Cannot publish an empty catalog");

        this.isPublished = true;
    }

    public void unpublish() {
        this.isPublished = false;
    }

    public Catalog addItem(CatalogItem item) {
        items.add(item);
        item.setCatalog(this);
        return this;
    }

    public Catalog removeItem(CatalogItem item) {
        items.remove(item);
        item.setCatalog(null);
        return this;
    }
}
