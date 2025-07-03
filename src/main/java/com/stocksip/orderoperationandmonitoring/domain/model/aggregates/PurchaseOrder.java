package com.stocksip.orderoperationandmonitoring.domain.model.aggregates;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateOrderCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Embedded
    @AttributeOverride(name= "value",
            column = @Column(name = "order_date", nullable = false))
    private OrderDate date;


    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status;
    public void changeStatus(OrderStatus newStatus) {
         if (this.status == OrderStatus.CANCELED) throw new IllegalStateException("No se puede mover desde CANCELED");
        this.status = newStatus;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId",      column = @Column(name = "buyer_account_id")),
            @AttributeOverride(name = "userOwnerId",    column = @Column(name = "buyer_user_owner_id")),
            @AttributeOverride(name = "role",           column = @Column(name = "buyer_role")),
            @AttributeOverride(name = "businessName",   column = @Column(name = "buyer_business_name")),
            @AttributeOverride(name = "email",          column = @Column(name = "buyer_email"))
    })
    private Buyer buyer;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId",      column = @Column(name = "supplier_account_id")),
            @AttributeOverride(name = "userOwnerId",    column = @Column(name = "supplier_user_owner_id")),
            @AttributeOverride(name = "role",           column = @Column(name = "supplier_role")),
            @AttributeOverride(name = "businessName",   column = @Column(name = "supplier_business_name")),
            @AttributeOverride(name = "email",          column = @Column(name = "supplier_email"))
    })
    private Supplier supplier;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "total_items", nullable = false)
    private Integer totalItems;

    protected PurchaseOrder() {}

    public PurchaseOrder(CreateOrderCommand cmd) {
        this.date = new OrderDate(LocalDateTime.now());
        this.status = OrderStatus.RECEIVED;
        this.buyer = cmd.buyer();
        this.supplier = cmd.supplier();
        this.items = cmd.items();
        this.totalAmount = cmd.totalAmount();
        this.totalItems = cmd.totalItems();
    }
}
