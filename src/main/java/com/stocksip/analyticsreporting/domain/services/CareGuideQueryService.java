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

    /**
     * Retrieves all care guides.
     *
     * @return a list of all care guides, or an empty list if none found
     */
    List<CareGuide> getAllCareGuide();
    /**
     * Handles a query to find care guides by guide name.
     *
     * @param query the query containing the guide name
     * @return a list of matching care guides, or an empty list if none found
     */
    List<CareGuide>handle(GetCareGuideByGuideNameQuery query);

    /**
     * Handles a query to find a care guide by ID.
     *
     * @param query the query containing the care guide ID
     * @return an Optional containing the found care guide, or empty if not found
     */
    Optional<CareGuide> handle(GetCareGuideByIdQuery query);
    /**
     * Handles a query to find care guides by type and description.
     *
     * @param query the query containing the type and description
     * @return an Optional containing the first matching care guide, or empty if none found
     */
    Optional<CareGuide> handle(GetCareGuideByTypeAndDescriptionQuery query);
}
