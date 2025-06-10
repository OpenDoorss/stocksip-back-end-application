package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.interfaces.rest.resources.ReportResource;

public class ReportResourceFromEntityAssembler {
    public static ReportResource toResourceFromEntity(Report entity) {
        return new ReportResource(entity.getId(),
                                  entity.getProductName(),
                                  entity.getType(),entity.getPrice(),
                                    entity.getAmount(),
                                  entity.getReportDate(),
                                  entity.getLostAmount());
    }
}
