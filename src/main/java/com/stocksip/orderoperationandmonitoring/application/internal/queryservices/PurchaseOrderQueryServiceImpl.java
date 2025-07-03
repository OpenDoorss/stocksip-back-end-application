package com.stocksip.orderoperationandmonitoring.application.internal.queryservices;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import com.stocksip.orderoperationandmonitoring.domain.services.PurchaseOrderQueryService;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderRepository repo;

    @Override
    public List<PurchaseOrder> findAll() {
        return repo.findAll();
    }

    @Override
    public List<PurchaseOrder> findByBuyerAccountId(Long buyerId) {
        return repo.findByBuyer_AccountId(buyerId);
    }

    @Override
    public List<PurchaseOrder> findBySupplierAccountId(Long supplierId) {
        return repo.findBySupplier_AccountId(supplierId);
    }
}
