package com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByBuyer_AccountId(Long id);

    List<PurchaseOrder> findBySupplier_AccountId(Long id);
}
