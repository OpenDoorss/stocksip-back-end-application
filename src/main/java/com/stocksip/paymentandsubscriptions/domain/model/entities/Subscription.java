package com.stocksip.paymentandsubscriptions.domain.model.entities;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "accountId")
    private Account Account;

    @Column(nullable = false)
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime endDate = LocalDateTime.now();
}
