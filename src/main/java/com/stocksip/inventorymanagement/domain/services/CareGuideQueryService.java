package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideByTypeAndDescriptionQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideProductAndWarehouseQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllCareGuidesByAccountIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * @name CareGuideQueryService
 *
 * @summary
 * This interface represents the service to handle care guide queries.
 * @since 1.0.0
 */
public interface CareGuideQueryService {
    List<CareGuide> handle(GetCareGuideByIdQuery query);
    List<CareGuide> handle (GetCareGuideByTypeAndDescriptionQuery query);
    List<CareGuide> handle(GetAllCareGuidesByAccountIdQuery query);
}
