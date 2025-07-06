package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.interfaces.rest.resources.ProductInventoryResource;

public class ProductInventoryResourceFromEntityAssembler {

    public static ProductInventoryResource toResourceFromEntity(Inventory inventory) {

        var product = inventory.getProduct();

        return new ProductInventoryResource(
                product.getProductId(),
                product.getProductName().name(),
                product.getBrandName().name(),
                product.getUnitPrice().amount(),
                product.getMinimumStock().getMinimumStock(),
                inventory.getProductStock().getStock(),
                inventory.getProductState().name(),
                inventory.getProductBestBeforeDate().bestBeforeDate());

    }
}
