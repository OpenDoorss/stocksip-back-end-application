package com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa;

import com.stocksip.paymentandsubscriptions.domain.model.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
