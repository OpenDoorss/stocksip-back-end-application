package com.stocksip.analyticsreporting.interfaces.rest.transform;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CareGuideResource;

/**
 * @summary 
 * This class is used to convert CareGuide entities to CareGuide resources.
 */
public class CareGuideResourceFromEntityAssembler {
    /**
     * Converts a CareGuide entity to a CareGuide resource.
     *
     * @param entity The CareGuide entity to convert.
     * @return The CareGuide resource.
     */
    public static CareGuideResource toResourceFromEntity(CareGuide entity){
        return new CareGuideResource(entity.getId(),entity.getGuideName(),entity.getType(),
                entity.getDescription());
    }
}
