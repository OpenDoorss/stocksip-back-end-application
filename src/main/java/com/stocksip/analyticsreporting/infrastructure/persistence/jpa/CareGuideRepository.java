package com.stocksip.analyticsreporting.infrastructure.persistence.jpa;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CareGuide entities.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface CareGuideRepository extends JpaRepository<CareGuide, Long> {
    List<CareGuide> findByGuideName(String guideName);

    List<CareGuide> findByTypeAndDescription(String type, String description);

    boolean existsByTypeAndDescription(String type, String description);

    Optional<CareGuide> findById(Long id);
}
