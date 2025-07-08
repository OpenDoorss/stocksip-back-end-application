package com.stocksip.paymentandsubscriptions.application.internal.queryservices;

import com.stocksip.paymentandsubscriptions.domain.model.queries.GetPlanTypeByAccountIdQuery;
import com.stocksip.paymentandsubscriptions.domain.services.SubscriptionQueryService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Override
    public Optional<String> handle(GetPlanTypeByAccountIdQuery query) {
        return subscriptionRepository.findPlanTypeByAccountId(query.accountId());
    }
}
