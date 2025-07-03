package com.stocksip.orderoperationandmonitoring.domain.services;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderQueryService {

    List<PurchaseOrder> findAll();
    List<PurchaseOrder> findByBuyerAccountId(Long buyerAccountId);

    List<PurchaseOrder> findBySupplierAccountId(Long supplierAccountId);
}
