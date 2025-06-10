package com.stocksip.analyticsreporting.domain.services;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;

import java.util.Optional;

/**
 * @name CareGuideCommandService
 * @summary
 * This interface represents the service to handle care guide commands.
 */
public interface CareGuideCommandService {
    /**
     * Handles the create care guide command.
     * @param command The create care guide command.
     * @return The created care guide.
     *
     * @throws IllegalArgumentException If guideName, type or description is null or empty
     * @see CreateCareGuideCommand
     */
    Optional<CareGuide> handle(CreateCareGuideCommand command);
    Optional<CareGuide> updateCareGuide(Long id, CreateCareGuideCommand command);
    boolean deleteCareGuide(Long id);
}
