package com.stocksip.paymentandsubscriptions.application.internal.commandservices;

import com.stocksip.paymentandsubscriptions.domain.model.commands.SeedPlansCommand;
import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import com.stocksip.paymentandsubscriptions.domain.services.PlanCommandService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.PlanRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the PlanCommandService interface for handling commands related to plans.
 * This service is responsible for seeding initial plans into the repository if none exist.
 *
 * @since 1.0.0
 */
@Service
public class PlanCommandServiceImpl implements PlanCommandService {

    private final PlanRepository planRepository;

    public PlanCommandServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /*
    * Handles the SeedPlansCommand to seed initial plans into the repository.
    */
    @Override
    public void handle(SeedPlansCommand command) {
        if (planRepository.count() == 0) {
            planRepository.save(Plan.createFreePlan());
            planRepository.save(Plan.createPremiumMonthly());
            planRepository.save(Plan.createPremiumAnnual());
        }
    }
}
