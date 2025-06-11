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
    /**
     * Finds all CareGuide entities by guide name.
     * @param guideName - the guide name to search for.
     * @return a list of CareGuide entities with the specified guide name.
     */
    List<CareGuide> findByGuideName(String guideName);
    
    /**
     * Finds all CareGuide entities by type and description.
     * @param type - the type to search for.
     * @param description - the description to search for.
     * @return a list of CareGuide entities with the specified type and description.
     */
    List<CareGuide> findByTypeAndDescription(String type, String description);

    /**
     * Checks if a CareGuide entity with the specified type and description exists.
     * @param type - the type to search for.
     * @param description - the description to search for.
     * @return true if a CareGuide entity with the specified type and description exists, false otherwise.
     */
    boolean existsByTypeAndDescription(String type, String description);

    /**
     * Finds a CareGuide entity by its ID.
     * @param id - the ID of the CareGuide entity to search for.
     * @return an Optional containing the CareGuide entity with the specified ID, or an empty Optional if no such entity exists.
     */
    Optional<CareGuide> findById(Long id);
}
