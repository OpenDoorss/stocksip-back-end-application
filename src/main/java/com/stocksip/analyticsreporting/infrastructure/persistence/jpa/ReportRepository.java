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
    /**
     * Finds all Report entities by report date.
     * @param reportDate - the report date to search for.
     * @return a list of Report entities with the specified report date.
     */
    List<Report> findByReportDate(Date reportDate);
    
    /**
     * Finds all Report entities by report date and lost amount.
     * @param reportDate - the report date to search for.
     * @param lostAmount - the lost amount to search for.
     * @return a list of Report entities with the specified report date and lost amount.
     */
    List<Report> findByReportDateAndLostAmount(Date reportDate, double lostAmount);
    
    /**
     * Finds all Report entities by product ID.
     * @param productId - the product ID to search for.
     * @return a list of Report entities with the specified product ID.
     */
    List<Report> findByProductId_ProductId(Long productId);
    
    /**
     * Finds all Report entities by type.
     * @param type - the type to search for.
     * @return a list of Report entities with the specified type.
     */
    List<Report> findByType(String type);
    
    /**
     * Checks if a Report entity with the specified report date and lost amount exists.
     * @param reportDate - the report date to search for.
     * @param lostAmount - the lost amount to search for.
     * @return true if a Report entity with the specified report date and lost amount exists, false otherwise.
     */
    boolean existsByReportDateAndLostAmount(Date reportDate, double lostAmount);
    
    /**
     * Checks if a Report entity with the specified product ID and ID not exists.
     * @param productId - the product ID to search for.
     * @param id - the ID to search for.
     * @return true if a Report entity with the specified product ID and ID not exists, false otherwise.
     */
    boolean existsByProductIdAndIdNot(String productId, Long id);
    
    /**
     * Finds a Report entity by its ID.
     * @param id - the ID of the Report entity to search for.
     * @return an Optional containing the Report entity with the specified ID, or an empty Optional if no such entity exists.
     */
    Optional<Report> findById(Long id);
}
