package com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories;

import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareGuideRepository extends JpaRepository<CareGuide, Long> {
}
