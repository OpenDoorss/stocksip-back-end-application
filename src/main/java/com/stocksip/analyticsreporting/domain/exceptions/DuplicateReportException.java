package com.stocksip.analyticsreporting.domain.exceptions;

/**
 * @summary
 * Exception thrown when attempting to create a report that already exists.
 * @param message - the message to be displayed.
 * @since 1.0
 */
public class DuplicateReportException extends RuntimeException {
    public DuplicateReportException(String message) {
        super(message);
    }
}
