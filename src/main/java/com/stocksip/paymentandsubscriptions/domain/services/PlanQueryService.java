package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAllPlansQuery;

import java.util.List;
import java.util.Optional;

public interface PlanQueryService {

    List<Plan> handle(GetAllPlansQuery query);
}
