package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProviderId;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CreateProductResource;

/**
 * This class is responsible for transforming a CreateProductResource into a CreateProductCommand.
 */
public class CreateProductCommandFromResourceAssembler {

    /**
     * This method transforms a CreateProductResource into a CreateProductCommand.
     * @param resource The CreateProductResource to transform.
     * @return The CreateProductCommand created from the resource.
     */
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {

        var targetProviderId = new ProviderId(resource.providerId());
        return new CreateProductCommand(
                resource.additionalName(),
                resource.liquorType(),
                resource.brandName(),
                resource.unitPriceAmount(),
                resource.minimumStock(),
                resource.image(),
                targetProviderId
        );
    }
}
