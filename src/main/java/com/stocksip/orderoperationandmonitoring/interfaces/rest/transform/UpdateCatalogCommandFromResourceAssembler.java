package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.UpdateCatalogCommand;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogResource;

/**
 * Maps {@link CatalogResource} to {@link UpdateCatalogCommand}.
 *
 * @since 1.0.0
 */
public class UpdateCatalogCommandFromResourceAssembler {

    public static UpdateCatalogCommand toCommandFromResource(Long catalogId,
                                                             CatalogResource resource) {
        return new UpdateCatalogCommand(
                catalogId,
                resource.accountId(),   // o new AccountId(resource.accountId())
                resource.name()         // o new CatalogName(resource.name())
        );
    }
}
