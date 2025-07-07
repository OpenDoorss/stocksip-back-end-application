package com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface defines the repository for managing Subscription entities.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findTopByAccount_AccountIdOrderByCreatedDateDesc(Long accountId);


    @Query("""
        SELECT s.plan.planId
        FROM Subscription s
        WHERE s.account.accountId = :accountId
          AND s.subscriptionStatus = 'COMPLETED'
        ORDER BY s.createdDate DESC
        LIMIT 1
    """)
    Optional<Long> findPlanIdByAccountId(@Param("accountId") Long accountId);
}
