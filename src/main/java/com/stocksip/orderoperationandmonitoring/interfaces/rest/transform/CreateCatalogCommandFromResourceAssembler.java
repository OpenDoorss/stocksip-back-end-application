package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateCatalogCommand;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogResource;

/**
 * Maps {@link CatalogResource} to {@link CreateCatalogCommand}.
 *
 * @since 1.0.0
 */
public class CreateCatalogCommandFromResourceAssembler {

    public static CreateCatalogCommand toCommandFromResource(CatalogResource resource) {
        Long accountId;
        Object rawAccountId = resource.accountId();
        accountId = Long.parseLong(rawAccountId.toString());
        return new CreateCatalogCommand(accountId, resource.name());
    }
}
