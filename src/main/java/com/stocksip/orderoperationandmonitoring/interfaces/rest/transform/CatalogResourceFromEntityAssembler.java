package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogResource;

public class CatalogResourceFromEntityAssembler {

    public static CatalogResource toResourceFromEntity(Catalog c) {
        return new CatalogResource(
                c.getCatalogId(),
                c.getAccountId().accountId(),
                c.getName().value(),
                c.getDateCreated().value(),
                c.isPublished()
        );
    }
}
