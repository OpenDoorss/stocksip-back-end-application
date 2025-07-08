package com.stocksip.paymentandsubscriptions.application.internal.queryservices;

import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAllPlansQuery;
import com.stocksip.paymentandsubscriptions.domain.services.PlanQueryService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }
}
