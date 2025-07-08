package com.stocksip.paymentandsubscriptions.interfaces.rest.transform;

import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.PlanResource;

public class PlansResourceFromEntityAssembler {

    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
                entity.getPlanId(),
                entity.getPlanType().toString(),
                entity.getDescription(),
                entity.getPrice().amount(),
                entity.getMaxWarehouses(),
                entity.getMaxProducts()
        );
    }
}
