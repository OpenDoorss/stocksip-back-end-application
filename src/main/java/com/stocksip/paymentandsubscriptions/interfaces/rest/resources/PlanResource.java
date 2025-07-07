package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

/**
 * This record represents a plan resource in the REST API.
 */
public record PlanResource(Long planId,
                           String PlanType,
                           String description,
                           Double price,
                           Integer maxWarehouses,
                           Integer maxProducts
                           ) {
}
