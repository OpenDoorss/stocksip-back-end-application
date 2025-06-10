package com.stocksip.analyticsreporting.application.internal.commandservices;

import com.stocksip.analyticsreporting.domain.exceptions.DuplicateReportException;
import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.services.CareGuideCommandService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.CareGuideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the CareGuideCommandService interface.
 * Handles command operations for CareGuide entities.
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
     * {@inheritDoc}
     */
    @Override
    public Optional<CareGuide> handle(CreateCareGuideCommand command){
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        if(careGuideRepository.existsByTypeAndDescription(
                command.type(),
                command.description())){
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }
        CareGuide careGuide = new CareGuide(command);
        CareGuide savedCareGuide = careGuideRepository.save(careGuide);

        return Optional.of(savedCareGuide);
    }
    @Override
    public Optional<CareGuide> updateCareGuide(Long id, CreateCareGuideCommand command) {
        if(command==null){
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        if(careGuideRepository.existsByTypeAndDescription(
                command.type(),
                command.description())) {
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }

        CareGuide careGuide=careGuideRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("CareGuide with ID " + id + " not found"));

        CareGuide updatedCareGuide = careGuideRepository.save(careGuide);
        return Optional.of(updatedCareGuide);
    }
    @Override
    public boolean deleteCareGuide(Long id){
        if (id == null) {
            throw new IllegalArgumentException("CareGuide ID cannot be null");
        }
        careGuideRepository.deleteById(id);
        return true;
    }

}
