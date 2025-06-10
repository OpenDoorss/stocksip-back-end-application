package com.stocksip.analyticsreporting.domain.exceptions;

/**
 * Exception thrown when attempting to create a report that already exists.
 */
public class DuplicateCareGuideException extends RuntimeException {
    public DuplicateCareGuideException(String message) {
        super(message);
    }
}
