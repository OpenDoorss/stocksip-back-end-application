package com.stocksip.analyticsreporting.application.internal.commandservices;

import com.stocksip.analyticsreporting.domain.exceptions.DuplicateReportException;
import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
import com.stocksip.analyticsreporting.domain.model.commands.DeleteReportCommand;
import com.stocksip.analyticsreporting.domain.model.commands.UpdateReportCommand;
import com.stocksip.analyticsreporting.domain.services.ReportCommandService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * ReportCommandService Implementation
 *
 * @summary
 * Implementation of the ReportCommandService interface.
 * It is responsible for handling report commands.
 *
 * @since 1.0
 */
@Service
@Transactional
public class ReportCommandServiceImpl implements ReportCommandService {
    
    private static final String REPORT_EXISTS_MSG = "A report with the same date and lost amount already exists";
    private static final String COMMAND_NULL_MSG = "CreateReportCommand cannot be null";
    
    private final ReportRepository reportRepository;

    public ReportCommandServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = Objects.requireNonNull(reportRepository, "ReportRepository cannot be null");
    }

    /**
     * Handles the creation of a new Report based on the provided command.
     *
     * @param command The command containing details for creating a new Report
     * @return An Optional containing the created Report if successful
     */
    @Override
    public Optional<Report> handle(CreateReportCommand command) {
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        if (reportRepository.existsByReportDateAndLostAmount(
                command.reportDate(), 
                command.lostAmount())) {
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }

        Report report = new Report(command);
        Report savedReport = reportRepository.save(report);
        
        return Optional.of(savedReport);
    }
    /**
     * Handles the update of an existing Report based on the provided command.
     *
     * @param command The command containing the update details
     * @return An Optional containing the updated Report if found and updated
     */
    @Override
    public Optional<Report> handle(UpdateReportCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("UpdateReportCommand cannot be null");
        }

        Report report = reportRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + command.id()));

        if (command.productId() != null && !command.productId().isBlank()) {
            Long productIdValue = Long.parseLong(command.productId());
            if (reportRepository.findByProductId_ProductId(productIdValue).stream()
                    .anyMatch(r -> !r.getId().equals(command.id()))) {
                throw new DuplicateReportException("A report with productId " + command.productId() + " already exists");
            }
        }

        report.updateInformation(
                command.productId(),
                command.type(),
                command.price(),
                command.amount(),
                command.reportDate(),
                command.lostAmount()
        );

        Report updatedReport = reportRepository.save(report);
        return Optional.of(updatedReport);
    }
    /**
     * Handles the deletion of a Report based on the provided command.
     *
     * @param command The command containing the ID of the Report to delete
     */
    @Override
    public void handle(DeleteReportCommand command) {
        if (command == null || command.id() == null) {
            throw new IllegalArgumentException("DeleteReportCommand and its ID cannot be null");
        }
        if (!reportRepository.existsById(command.id())) {
            throw new IllegalArgumentException("Report not found");
        }
        reportRepository.deleteById(command.id());
    }
    
}
