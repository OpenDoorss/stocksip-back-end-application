package com.stocksip.paymentandsubscriptions.domain.model.entities;

import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.PaymentFrequency;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.PlanType;
import com.stocksip.shared.domain.model.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Enumerated(EnumType.STRING)
    private PlanType planType;

    public String description;

    @Enumerated(EnumType.STRING)
    public PaymentFrequency paymentFrequency;

    @Embedded
    public Money price;

    private int maxWarehouses;

    private int maxProducts;

    /**
     * Default constructor for JPA
     */
    protected Plan() {}

    public Plan(PlanType planType, String description, PaymentFrequency paymentFrequency, Money price, int maxWarehouses, int maxProducts) {
        this.planType = planType;
        this.description = description;
        this.paymentFrequency = paymentFrequency;
        this.price = price;
        this.maxWarehouses = maxWarehouses;
        this.maxProducts = maxProducts;

        validate();
    }

    public static Plan createFreePlan() {
        return new Plan(
                PlanType.Free,
                "Free Plan",
                PaymentFrequency.None,
                new Money(0.0, "USD"),
                4,
                50
        );
    }

    public static Plan createPremiumMonthly() {
        return new Plan(
                PlanType.PremiumMonthly,
                "Monthly Premium Plan",
                PaymentFrequency.Monthly,
                new Money(29.99, "USD"),
                10,
                100
        );
    }

    public static Plan createPremiumAnnual() {
        return new Plan(
                PlanType.PremiumAnnual,
                "Annual Premium Plan",
                PaymentFrequency.Annual,
                new Money(299.99, "USD"),
                20,
                200
        );
    }

    private void validate() {
        if (planType == PlanType.Free && price.amount() > 0)
            throw new IllegalArgumentException("The FREE plan should not have a price greater than 0");

        if (planType == PlanType.Free && paymentFrequency != PaymentFrequency.None)
            throw new IllegalArgumentException("The FREE plan should have a payment frequency of NONE");

        if (planType == PlanType.PremiumMonthly && price.amount() <= 0)
            throw new IllegalArgumentException("The PREMIUM MONTHLY plan should have a price greater than 0");

        if (planType == PlanType.PremiumMonthly && paymentFrequency != PaymentFrequency.Monthly)
            throw new IllegalArgumentException("The PREMIUM MONTHLY plan should have a payment frequency of MONTHLY");

        if (planType == PlanType.PremiumAnnual && paymentFrequency != PaymentFrequency.Annual)
            throw new IllegalArgumentException("The PREMIUM ANNUAL plan should have a payment frequency of ANNUAL");
    }

}
