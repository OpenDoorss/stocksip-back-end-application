package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.commands.CreateReportCommand;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CreateReportResource;

public class CreateReportCommandFromResourceAssembler {
    public static CreateReportCommand toCommandFromResource(CreateReportResource resource) {
        return new CreateReportCommand(resource.productName(),resource.type(),resource.price(),resource.amount(), resource.reportDate(), resource.lostAmount());
    }
}
