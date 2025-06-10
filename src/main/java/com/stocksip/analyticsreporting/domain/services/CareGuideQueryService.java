package com.stocksip.analyticsreporting.domain.services;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * @name CareGuideQueryService
 *
 * @summary
 * This interface represents the service to handle care guide queries.
 * @since 1.0.0
 */
public interface CareGuideQueryService {
    /**
     * Retrieves a care guide by its ID.
     *
     * @param id the ID of the care guide to retrieve
     * @return an Optional containing the found care guide, or empty if not found
     */
    Optional<CareGuide> getCareGuideById(Long id);
    List<CareGuide> getAllCareGuide();
    List<CareGuide>handle(GetCareGuideByGuideNameQuery query);
    Optional<CareGuide> handle(GetCareGuideByIdQuery query);
    Optional<CareGuide> handle(GetCareGuideByTypeAndDescriptionQuery query);
}
