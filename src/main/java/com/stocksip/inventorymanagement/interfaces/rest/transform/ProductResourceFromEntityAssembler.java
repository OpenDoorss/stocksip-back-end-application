package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.interfaces.rest.resources.ProductResource;

/**
 * This class is responsible for transforming a Product entity into a ProductResource.
 */
public class ProductResourceFromEntityAssembler {

    /**
     * This method transforms a Product entity into a ProductResource.
     * @param entity The Product entity to be transformed into a ProductResource.
     * @return A ProductResource that contains the details of the product.
     */
    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getProductId(),
                entity.getImageUrl().imageUrl(),
                entity.getProductName().name(),
                entity.getBrandName().name(),
                entity.getLiquorType().name(),
                entity.getUnitPrice().amount(),
                entity.getMinimumStock().minimumStock(),
                entity.getAccountId().accountId()
        );
    }
}
