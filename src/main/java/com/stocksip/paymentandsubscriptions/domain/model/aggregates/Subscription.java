package com.stocksip.paymentandsubscriptions.domain.model.aggregates;

import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private LocalDateTime createdDate;

    private LocalDateTime expiredDate;

    /**
     * Default constructor for JPA
     */
    protected  Subscription() {}

    public Subscription(Account account, Plan plan) {
        this.account = account;
        this.plan = plan;
        this.subscriptionStatus = SubscriptionStatus.COMPLETED;
        this.createdDate = LocalDateTime.now();
        this.expiredDate = calculateExpirationDate(plan);
    }

    public void activateWithPlan(Plan newPlan) {
        this.plan = newPlan;
        this.subscriptionStatus = SubscriptionStatus.COMPLETED;
        this.expiredDate = calculateExpirationDate(newPlan);

        if ("INACTIVE".equals(account.getStatus())) {
            account.activateAccount();
        }
    }

    public void markAsCompleted() {
        this.subscriptionStatus = SubscriptionStatus.COMPLETED;
    }

    public void upgradePlan(Plan newPlan) {
        if (newPlan == null) throw new IllegalArgumentException("Plan cannot be null");
        if (plan.getPlanId().equals(newPlan.getPlanId())) throw new IllegalStateException("Already on this plan");

        this.plan = newPlan;
        this.expiredDate = calculateExpirationDate(newPlan);
        if (subscriptionStatus != SubscriptionStatus.COMPLETED)
            subscriptionStatus = SubscriptionStatus.COMPLETED;

        if ("INACTIVE".equals(account.getStatus()))
            account.activateAccount();
    }

    private LocalDateTime calculateExpirationDate(Plan plan) {
        return switch (plan.getPaymentFrequency()) {
            case Monthly -> LocalDateTime.now().plusMonths(1);
            case Annual -> LocalDateTime.now().plusYears(1);
            case None -> LocalDateTime.MAX;
        };
    }

}
