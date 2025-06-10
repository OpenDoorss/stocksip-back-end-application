package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CareGuideResource;

public class CareGuideResourceFromEntityAssembler {
    public static CareGuideResource toResourceFromEntity(CareGuide entity){
        return new CareGuideResource(entity.getId(),entity.getGuideName(),entity.getType(),
                entity.getDescription());
    }
}
