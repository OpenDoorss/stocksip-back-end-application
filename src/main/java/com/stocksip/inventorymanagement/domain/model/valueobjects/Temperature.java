package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * This is a value object that represents the temperature range of a warehouse.
 * @param minTemperature The minimum temperature in degrees Celsius.
 * @param maxTemperature The maximum temperature in degrees Celsius.
 *
 * @summary
 * The WarehouseTemperature class is an embeddable value object that encapsulates the temperature range of a warehouse.
 *
 * @since 1.0.0
 */
@Embeddable
public record Temperature(double minTemperature, double maxTemperature) {


    /*
     * The minimum temperature limit for the warehouse.
     */
    private static final double MIN_TEMPERATURE = -50.0;
    /*
     * The maximum temperature limit for the warehouse.
     */
    private static final double MAX_TEMPERATURE = 50.0;

    /**
     * This constructor validates the input parameters to ensure that they are within the specified range.
     *
     * @param minTemperature The minimum temperature in degrees Celsius.
     * @param maxTemperature The maximum temperature in degrees Celsius.
     *
     * @throws IllegalArgumentException if the temperatures are not within the range of -50.0 to 50.0 degrees Celsius,
     * or if maxTemperature is not greater than minTemperature.
     */
    public Temperature {
        validateTemperature(minTemperature, maxTemperature);
    }

    /**
     * Validates the temperature range.
     *
     * @param maxTemperature The maximum temperature in degrees Celsius.
     * @param minTemperature The minimum temperature in degrees Celsius.
     *
     * @throws IllegalArgumentException if the temperatures are not within the specified range or if maxTemperature is not greater than minTemperature.
     */
    private static void validateTemperature(double minTemperature, double maxTemperature) {

        if (minTemperature >= maxTemperature) {
            throw new IllegalArgumentException("The maximum temperature must be greater than the minimum temperature.");
        }

        if (minTemperature < MIN_TEMPERATURE || minTemperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException("The minimum temperature must be between " + MIN_TEMPERATURE + " and " + MAX_TEMPERATURE + " degrees Celsius.");
        }

        if (maxTemperature < MIN_TEMPERATURE || maxTemperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException("The maximum temperature must be between " + MIN_TEMPERATURE + " and " + MAX_TEMPERATURE + " degrees Celsius.");
        }
    }

}
