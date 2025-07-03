package com.stocksip.orderoperationandmonitoring.interfaces.rest.transform;

import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateCatalogItemCommand;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogItemResource;

/**
 * Transforms a {@link CatalogItemResource} into a {@link CreateCatalogItemCommand}.
 *
 * <p>This mirrors the pattern used for other assembler classes in the codebase, keeping
 * controllers lightweight and concentrating mapping logic in one place.</p>
 *
 * @since 1.0.0
 */
public class CreateCatalogItemCommandFromResourceAssembler {

    /**
     * Converts the supplied {@link CatalogItemResource} into a {@link CreateCatalogItemCommand}.
     *
     * @param resource  the inbound REST resource representing the catalog item to create
     * @param catalogId the identifier of the catalog that the new item belongs to
     * @return a new instance of {@link CreateCatalogItemCommand}
     */
    public static CreateCatalogItemCommand toCommandFromResource(CatalogItemResource resource, Long catalogId) {
        return new CreateCatalogItemCommand(
                catalogId,
                resource.name(),
                resource.productType(),
                resource.brand(),
                resource.content(),
                resource.unitPrice()
        );
    }
}
