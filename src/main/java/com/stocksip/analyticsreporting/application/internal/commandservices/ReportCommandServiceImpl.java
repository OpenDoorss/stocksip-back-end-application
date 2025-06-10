package com.stocksip.analyticsreporting.application.internal.commandservices;

import com.stocksip.analyticsreporting.domain.exceptions.DuplicateReportException;
import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
import com.stocksip.analyticsreporting.domain.services.ReportCommandService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the ReportCommandService interface.
 * Handles command operations for Report entities.
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
     * {@inheritDoc}
     */
    @Override
    public Optional<Report> handle(CreateReportCommand command) {
        // Input validation
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        
        // Check for duplicate report
        if (reportRepository.existsByReportDateAndLostAmount(
                command.reportDate(), 
                command.lostAmount())) {
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }
        
        // Create and save the new report
        Report report = new Report(command);
        Report savedReport = reportRepository.save(report);
        
        return Optional.of(savedReport);
    }

    @Override
    public Optional<Report> updateReport(Long id, CreateReportCommand command) {
        // Input validation
        if (command == null) {
            throw new IllegalArgumentException(COMMAND_NULL_MSG);
        }
        
        // Check for duplicate report
        if (reportRepository.existsByReportDateAndLostAmount(
                command.reportDate(), 
                command.lostAmount())) {
            throw new DuplicateReportException(REPORT_EXISTS_MSG);
        }
        
        // Update the existing report
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));

        //report.update(command);
        Report updatedReport = reportRepository.save(report);
        
        return Optional.of(updatedReport);
    }

    @Override
    public boolean deleteReport(Long id) {
        // Input validation
        if (id == null) {
            throw new IllegalArgumentException("Report ID cannot be null");
        }
        
        // Delete the report
        reportRepository.deleteById(id);
        return true;
    }
    
}
