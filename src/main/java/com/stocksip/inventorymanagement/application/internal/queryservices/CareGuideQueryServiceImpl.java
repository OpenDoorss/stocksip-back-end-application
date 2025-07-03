package com.stocksip.inventorymanagement.application.internal.queryservices;

import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideByTypeAndDescriptionQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllCareGuidesByAccountIdQuery;
import com.stocksip.inventorymanagement.domain.services.CareGuideQueryService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.CareGuideRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareGuideQueryServiceImpl implements CareGuideQueryService {

    private final CareGuideRepository careGuideRepository;

    public CareGuideQueryServiceImpl(CareGuideRepository careGuideRepository) {
        this.careGuideRepository = careGuideRepository;
    }

    @Override
    public List<CareGuide> handle(GetCareGuideByIdQuery query) {
        return careGuideRepository.findById(query.id())
                .map(List::of)
                .orElseGet(List::of);
    }

    @Override
    public List<CareGuide> handle(GetCareGuideByTypeAndDescriptionQuery query) {
        return careGuideRepository.findByTypeAndDescription(
                query.type(), 
                query.description()
        );
    }
    @Override
    public List<CareGuide> handle(GetAllCareGuidesByAccountIdQuery query) {
        return careGuideRepository.findByAccountId(query.accountId());
    }
}