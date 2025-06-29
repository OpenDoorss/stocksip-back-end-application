package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;

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

    /**
     * Handles the update care guide command.
     * @param command The update care guide command.
     * @return The updated care guide.
     *
     * @throws IllegalArgumentException If id, guideName, type or description is null
     * @see UpdateCareGuideCommand
     */
    Optional<CareGuide> handle(UpdateCareGuideCommand command);

    /**
     * Handles the delete care guide command.
     * @param command The delete care guide command.
     *
     * @throws IllegalArgumentException If id is null or less than or equal to 0
     * @see DeleteCareGuideCommand
     */
    void handle(DeleteCareGuideCommand command);    
}
