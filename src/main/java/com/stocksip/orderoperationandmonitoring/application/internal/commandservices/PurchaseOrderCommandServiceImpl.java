package com.stocksip.orderoperationandmonitoring.application.internal.commandservices;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateOrderCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogQueryService;
import com.stocksip.orderoperationandmonitoring.domain.services.PurchaseOrderCommandService;
import com.stocksip.orderoperationandmonitoring.infrastructure.persistence.jpa.repositories.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository repo;
    private final CatalogQueryService catalogQry;

    @Override
    public Optional<PurchaseOrder> handle(CreateOrderCommand cmd) {
        PurchaseOrder po = new PurchaseOrder(cmd);
        return Optional.of(repo.save(po));
    }

    @Override
    public void changeStatus(Long id, OrderStatus newStatus) {
        PurchaseOrder po = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        po.changeStatus(newStatus);
        repo.save(po);
    }
}
