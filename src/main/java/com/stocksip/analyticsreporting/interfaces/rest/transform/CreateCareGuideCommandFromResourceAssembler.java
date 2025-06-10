package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CreateCareGuideResource;

public class CreateCareGuideCommandFromResourceAssembler {
    public static CreateCareGuideCommand toCommandFromResource(CreateCareGuideResource resource) {
        return new CreateCareGuideCommand(resource.guideName(),
                resource.type(),resource.description());
    }
}
