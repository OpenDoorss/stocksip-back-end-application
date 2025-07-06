package com.stocksip.alertsandnotifications.domain.services;

import com.stocksip.alertsandnotifications.domain.model.aggregates.Alert;
import com.stocksip.alertsandnotifications.domain.model.queries.GetAlertByIdQuery;
import com.stocksip.alertsandnotifications.domain.model.queries.GetAllAlertsByProductIdQuery;
import com.stocksip.alertsandnotifications.domain.model.queries.GetAllAlertsByAccountIdQuery;
import com.stocksip.alertsandnotifications.domain.model.queries.GetAllAlertsByWarehouseIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the Alert Query Service.
 *
 * @summary
 * This interface defines the contract for handling alert-related queries,
 * including retrieval of alerts by various criteria.
 *
 * @since 1.0
 */
public interface AlertQueryService {

    /**
     * Handles the retrieval of an alert by its ID.
     *
     * @param query The query containing the alert ID.
     * @return The alert with the specified ID, or empty if not found.
     */
    Optional<Alert> handle(GetAlertByIdQuery query);
    
    /**
     * Handles the retrieval of all alerts for a specific product ID.
     *
     * @param query The query containing the product ID.
     * @return A list of alerts associated with the specified product ID.
     */
    List<Alert> handle(GetAllAlertsByProductIdQuery query);

    /**
     * Handles the retrieval of all alerts for a specific profile ID.
     *
     * @param query The query containing the profile ID.
     * @return A list of alerts associated with the specified profile ID.
     */
    List<Alert> handle(GetAllAlertsByAccountIdQuery query);

    /**
     * Handles the retrieval of all alerts for a specific warehouse ID.
     *
     * @param query The query containing the warehouse ID.
     * @return A list of alerts associated with the specified warehouse ID.
     */
    List<Alert> handle(GetAllAlertsByWarehouseIdQuery query);
} 