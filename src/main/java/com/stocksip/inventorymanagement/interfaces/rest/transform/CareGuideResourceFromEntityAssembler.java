package com.stocksip.inventorymanagement.interfaces.rest.transform;
import com.stocksip.inventorymanagement.domain.model.entities.CareGuide;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CareGuideResource;

public class CareGuideResourceFromEntityAssembler {
    public static CareGuideResource toResourceFromEntity(CareGuide entity) {
        return new CareGuideResource(
                entity.getId(),
                entity.getGuideName(),
                entity.getType(),
                entity.getDescription(),
                entity.getImageUrl() != null ? entity.getImageUrl().imageUrl() : null
        );
    }
}
