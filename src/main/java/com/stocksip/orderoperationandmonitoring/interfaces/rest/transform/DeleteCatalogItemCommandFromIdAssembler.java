package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.DeleteCatalogItemCommand;

/**
 * Maps catalogItemId to {@link DeleteCatalogItemCommand}.
 *
 * @since 1.0.0
 */
public class DeleteCatalogItemCommandFromIdAssembler {

    public static DeleteCatalogItemCommand toCommandFromId(String catalogItemId) {
        return new DeleteCatalogItemCommand(catalogItemId);
    }
}
