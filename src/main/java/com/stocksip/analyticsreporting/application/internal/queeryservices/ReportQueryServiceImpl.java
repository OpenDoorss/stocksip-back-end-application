package com.stocksip.analyticsreporting.application.internal.queeryservices;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.queries.*;
import com.stocksip.analyticsreporting.domain.services.ReportQueryService;
import com.stocksip.analyticsreporting.infrastructure.persistence.jpa.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ReportQueryService interface.
 * Handles query operations for Report entities.
 */
@Service
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;

    public ReportQueryServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    
    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<Report> handle(GetReportByProductNameQuery query) {
        if (query == null || query.productName() == null || query.productName().isBlank()) {
            throw new IllegalArgumentException("Query and product name must not be null or empty");
        }
        return reportRepository.findByProductName(query.productName());
    }

    @Override
    public Optional<Report> handle(GetReportByIdQuery query) {
        if (query == null || query.id() == null) {
            throw new IllegalArgumentException("Query and report ID must not be null");
        }
        return reportRepository.findById(query.id());
    }

    @Override
    public Optional<Report> handle(GetReportByReportDateAndLostAmountQuery query) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        List<Report> reports = reportRepository.findByReportDateAndLostAmount(
            query.reportDate(), 
            query.lostAmount()
        );
        return reports.isEmpty() ? Optional.empty() : Optional.of(reports.get(0));
    }

    @Override
    public Optional<Report> handle(GetReportByTypeQuery query) {
        if (query == null || query.type() == null || query.type().isBlank()) {
            throw new IllegalArgumentException("Query and report type must not be null or empty");
        }
        return reportRepository.findByType(query.type()).stream().findFirst();
    }
}
