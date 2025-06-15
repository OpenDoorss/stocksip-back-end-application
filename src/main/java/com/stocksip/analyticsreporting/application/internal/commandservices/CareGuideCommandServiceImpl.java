package com.stocksip.analyticsreporting.application.internal.commandservices;

import com.stocksip.analyticsreporting.domain.exceptions.DuplicateReportException;
import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.model.commands.DeleteCareGuideCommand;
import com.stocksip.analyticsreporting.domain.model.commands.UpdateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.services.CareGuideCommandService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.CareGuideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * CareGuideCommandService Implementation
 *
 * @summary
 * Implementation of the CareGuideCommandService interface.
 * It is responsible for handling care guide commands.
 *
 * @since 1.0
 */
@Service
@Transactional
public class CareGuideCommandServiceImpl implements CareGuideCommandService {

    private static final String REPORT_EXISTS_MSG = "A care guide with the same date and lost amount already exists";
    private static final String COMMAND_NULL_MSG = "CreateCareGuideCommand cannot be null";

    private final CareGuideRepository careGuideRepository;

    public CareGuideCommandServiceImpl(CareGuideRepository careGuideRepository) {
        this.careGuideRepository = Objects.requireNonNull(careGuideRepository, "CareGuideRepository cannot be null");
    }
    /**
     * Handles the creation of a new CareGuide based on the provided command.
     *
     * @param command The command containing details for creating a new CareGuide
     * @return An Optional containing the created CareGuide if successful
     */
    @Override
    public Optional<CareGuide> handle(CreateCareGuideCommand command) {
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        if(careGuideRepository.existsByTypeAndDescription(
                command.type(),
                command.description())){
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }
        // Create a new CareGuide with the command and default image URL
        CareGuide careGuide = new CareGuide(command, command.imageUrl());
        CareGuide savedCareGuide = careGuideRepository.save(careGuide);

        return Optional.of(savedCareGuide);
    }
    /**
     * Handles the update of an existing CareGuide based on the provided command.
     *
     * @param command The command containing the update details
     * @return An Optional containing the updated CareGuide if found and updated
     */
    @Override
    public Optional<CareGuide> handle(UpdateCareGuideCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("UpdateReportCommand cannot be null");
        }
        CareGuide careGuide= careGuideRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("CareGuide with ID " + command.id() + " not found"));

        if (command.type() != null && !command.type().isBlank()) {
            if (careGuideRepository.existsByTypeAndDescription(command.type(), command.description())) {
                throw new DuplicateReportException("A care guide with the same name already exists");
            }   
        }
        // Update the care guide with all fields including imageUrl
        careGuide.updateInformation(command.guideName(), command.type(), command.description(), command.imageUrl());

        CareGuide updatedCareGuide = careGuideRepository.save(careGuide);
        return Optional.of(updatedCareGuide);
    }
    /**
     * Handles the deletion of a CareGuide based on the provided command.
     *
     * @param command The command containing the ID of the CareGuide to delete
     */
    @Override
    public void handle(DeleteCareGuideCommand command) {
        if (command == null|| command.id() == null) {
            throw new IllegalArgumentException("DeleteCareGuideCommand cannot be null");
        }
        if (!careGuideRepository.existsById(command.id())) {
            throw new IllegalArgumentException("CareGuide with ID " + command.id() + " not found");
        }
        careGuideRepository.deleteById(command.id());
    }

}
