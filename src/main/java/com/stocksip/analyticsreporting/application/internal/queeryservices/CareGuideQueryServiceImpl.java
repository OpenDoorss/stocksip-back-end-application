package com.stocksip.analyticsreporting.application.internal.queeryservices;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.queries.GetCareGuideByGuideNameQuery;
import com.stocksip.analyticsreporting.domain.model.queries.GetCareGuideByIdQuery;
import com.stocksip.analyticsreporting.domain.model.queries.GetCareGuideByTypeAndDescriptionQuery;
import com.stocksip.analyticsreporting.domain.services.CareGuideQueryService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.CareGuideRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the CareGuideQueryService interface.
 * Handles query operations for CareGuide entities.
 */
@Service
public class CareGuideQueryServiceImpl implements CareGuideQueryService {

    private final CareGuideRepository careGuideRepository;

    public CareGuideQueryServiceImpl(CareGuideRepository careGuideRepository) {
        this.careGuideRepository = careGuideRepository;
    }

    @Override
    public List<CareGuide> getAllCareGuide() {
        return careGuideRepository.findAll();
    }

    @Override
    public Optional<CareGuide> getCareGuideById(Long id) {
        return careGuideRepository.findById(id);
    }

    @Override
    public List<CareGuide> handle(GetCareGuideByGuideNameQuery query) {
        if (query == null || query.guideName().isBlank()) {
            throw new IllegalArgumentException("Care Guide name must not be null or empty");
        }
        return careGuideRepository.findByGuideName(query.guideName());
    }
    @Override
    public Optional<CareGuide> handle(GetCareGuideByIdQuery query){
        if(query==null|| query.id()==null){
            throw new IllegalArgumentException("Query and care guide ID must not be null");
        }
        return careGuideRepository.findById(query.id());
    }
    @Override
    public  Optional<CareGuide>handle(GetCareGuideByTypeAndDescriptionQuery query){
        if (query==null) {
            throw new IllegalArgumentException("Query, type, and description must not be null or empty");
        }
       List<CareGuide> careGuides= careGuideRepository.findByTypeAndDescription(
            query.type(),
            query.description()
        );
        return careGuides.isEmpty() ? Optional.empty() : Optional.of(careGuides.get(0));
    }

}
