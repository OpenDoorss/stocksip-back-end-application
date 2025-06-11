package com.stocksip.analyticsreporting.domain.exceptions;

/**
 * @summary
 * Exception thrown when attempting to create a care guide that already exists.
 * @param message - the message to be displayed.
 * @since 1.0
 */
public class DuplicateCareGuideException extends RuntimeException {
    public DuplicateCareGuideException(String message) {
        super(message);
    }
}
