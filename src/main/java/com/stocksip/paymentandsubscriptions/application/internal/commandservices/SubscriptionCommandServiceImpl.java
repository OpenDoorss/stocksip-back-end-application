package com.stocksip.paymentandsubscriptions.application.internal.commandservices;

import com.stocksip.paymentandsubscriptions.application.internal.outboundservices.payments.PaymentService;
import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Subscription;
import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteUpgradeCommand;
import com.stocksip.paymentandsubscriptions.domain.model.commands.SubscribeToPlanCommand;
import com.stocksip.paymentandsubscriptions.domain.model.commands.UpgradeSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.PlanType;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.stocksip.paymentandsubscriptions.domain.services.SubscriptionCommandService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.PlanRepository;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.SubscriptionRepository;
import com.stocksip.shared.infrastructure.spa.configuration.FrontendConfiguration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final AccountRepository accountRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentService paymentService;
    private final FrontendConfiguration frontendProperties;

    public SubscriptionCommandServiceImpl(
            AccountRepository accountRepository,
            PlanRepository planRepository,
            SubscriptionRepository subscriptionRepository,
            PaymentService paymentService,
            FrontendConfiguration frontendProperties) {
        this.accountRepository = accountRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentService = paymentService;
        this.frontendProperties = frontendProperties;
    }

    @Override
    public Optional<String> handle(SubscribeToPlanCommand command) {
        Account account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Plan plan = planRepository.findById(command.planId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription latestSubscription = subscriptionRepository.findTopByAccount_AccountIdOrderByCreatedDateDesc(command.accountId());
        if (latestSubscription != null && latestSubscription.getSubscriptionStatus() == SubscriptionStatus.COMPLETED) {
            throw new IllegalStateException("You already have an active subscription.");
        }

        if (plan.getPlanType() == PlanType.Free) {
            Subscription subscription = new Subscription(account, plan);
            subscription.activateWithPlan(plan);
            subscriptionRepository.save(subscription);
            accountRepository.save(account);
            return Optional.of("/dashboard");
        }

        String approvalUrl = paymentService.createOrder(
                plan.getPlanType().toString(),
                plan.getPrice().amount(),
                frontendProperties.getBaseUrl() + "/payments-success?accountId=" + command.accountId() + "&planId=" + command.planId(),
                frontendProperties.getBaseUrl() + "/payments-cancel"
        );

        return Optional.ofNullable(approvalUrl);
    }

    @Override
    public void handle(CompleteSubscriptionCommand command) {
        Account account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Plan plan = planRepository.findById(command.planId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        paymentService.captureOrder(command.token());

        Subscription subscription = new Subscription(account, plan);
        subscription.markAsCompleted();
        account.activateAccount();

        subscriptionRepository.save(subscription);
        accountRepository.save(account);
    }

    @Override
    public Optional<String> handle(UpgradeSubscriptionCommand command) {
        Account account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Plan upgradePlan = planRepository.findById(command.planId())
                .orElseThrow(() -> new RuntimeException("Upgrade plan not found"));

        if (upgradePlan.getPlanType() == PlanType.Free) {
            throw new RuntimeException("Cannot upgrade to a free plan");
        }

        String approvalUrl = paymentService.createOrder(
                upgradePlan.getPlanType().toString(),
                upgradePlan.getPrice().amount(),
                frontendProperties.getBaseUrl() + "/payments-upgrade-success?accountId=" + command.accountId() + "&planId=" + command.planId(),
                frontendProperties.getBaseUrl() + "/payments-cancel"
        );

        return Optional.ofNullable(approvalUrl);
    }

    @Override
    public void handle(CompleteUpgradeCommand command) {

    }
}
