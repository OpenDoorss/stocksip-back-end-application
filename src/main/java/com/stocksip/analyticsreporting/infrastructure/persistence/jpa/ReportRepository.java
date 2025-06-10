package com.stocksip.analyticsreporting.infrastructure.persistence.jpa;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Report entities.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    List<Report> findByReportDate(Date reportDate);
    
    List<Report> findByReportDateAndLostAmount(Date reportDate, double lostAmount);
    
    List<Report> findByProductName(String productName);
    
    List<Report> findByType(String type);
    
    boolean existsByReportDateAndLostAmount(Date reportDate, double lostAmount);
    
    Optional<Report> findById(Long id);
}
