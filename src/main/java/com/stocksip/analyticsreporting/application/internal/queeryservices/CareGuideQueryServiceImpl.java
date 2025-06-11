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
 * CareGuideQueryService Implementation
 *
 * @summary
 * Implementation of the CareGuideQueryService interface.
 * It is responsible for handling care guide queries.
 *
 * @since 1.0
 */
@Service
public class CareGuideQueryServiceImpl implements CareGuideQueryService {

    private final CareGuideRepository careGuideRepository;

    public CareGuideQueryServiceImpl(CareGuideRepository careGuideRepository) {
        this.careGuideRepository = careGuideRepository;
    }

    /**
     * Retrieves all care guides from the repository.
     *
     * @return A list of all CareGuide entities
     */
    @Override
    public List<CareGuide> getAllCareGuide() {
        return careGuideRepository.findAll();
    }

    /**
     * Retrieves a care guide by its unique identifier.
     *
     * @param id The ID of the care guide to retrieve
     * @return An Optional containing the found CareGuide or empty if not found
     */
    @Override
    public Optional<CareGuide> getCareGuideById(Long id) {
        return careGuideRepository.findById(id);
    }
    /**
     * Handles the retrieval of care guides by guide name.
     *
     * @param query The query containing the guide name to search for
     * @return A list of CareGuide entities matching the guide name
     */
    @Override
    public List<CareGuide> handle(GetCareGuideByGuideNameQuery query) {
        if (query == null || query.guideName().isBlank()) {
            throw new IllegalArgumentException("Care Guide name must not be null or empty");
        }
        return careGuideRepository.findByGuideName(query.guideName());
    }

    /**
     * Handles the retrieval of a care guide by its ID.
     *
     * @param query The query containing the ID to search for
     * @return An Optional containing the found CareGuide or empty if not found
     */
    @Override
    public Optional<CareGuide> handle(GetCareGuideByIdQuery query) {
        if (query == null || query.id() == null) {
            throw new IllegalArgumentException("Query and care guide ID must not be null");
        }
        return careGuideRepository.findById(query.id());
    }

    /**
     * Handles the retrieval of a care guide by type and description.
     *
     * @param query The query containing the type and description to search for
     * @return An Optional containing the first matching CareGuide or empty if none found
     */
    @Override
    public Optional<CareGuide> handle(GetCareGuideByTypeAndDescriptionQuery query) {
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
