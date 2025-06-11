package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.interfaces.rest.resources.ReportResource;

/**
 * @summary 
 * This class is used to convert Report entities to Report resources.
 */
public class ReportResourceFromEntityAssembler {
    /**
     * Converts a Report entity to a Report resource.
     *
     * @param entity The Report entity to convert.
     * @return The Report resource.
     */
    public static ReportResource toResourceFromEntity(Report entity) {
        return new ReportResource(entity.getId(),
                                entity.getProductId().productId().toString(),
                                entity.getType(),
                                entity.getPrice(),
                                entity.getAmount(),
                                entity.getReportDate(),
                                entity.getLostAmount());
    }
}
