package com.stocksip.alertsandnotifications.interfaces.rest.transform;

import com.stocksip.alertsandnotifications.domain.model.commands.CreateAlertCommand;
import com.stocksip.alertsandnotifications.interfaces.rest.resources.CreateAlertResource;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.AccountId;

/**
 * This static class is responsible for transforming a CreateAlertResource into a CreateAlertCommand.
 *
 * @summary
 * Static utility class that provides methods to transform CreateAlertResource
 * objects into CreateAlertCommand objects for use in the application layer.
 *
 * @since 1.0
 */
public final class CreateAlertCommandFromResourceAssembler {

    // Private constructor to prevent instantiation
    private CreateAlertCommandFromResourceAssembler() {}

    /**
     * This method transforms a CreateAlertResource into a CreateAlertCommand.
     *
     * @param resource The resource containing the details for creating a new alert.
     * @return The command that can be used to create a new alert.
     */
    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource) {
        return new CreateAlertCommand(
                resource.title(),
                resource.message(),
                resource.severity(),
                resource.type(),
                resource.accountId(),
                resource.productId(),
                resource.warehouseId()
        );
    }
} 