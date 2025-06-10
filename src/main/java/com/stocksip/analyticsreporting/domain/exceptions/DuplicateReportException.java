package com.stocksip.analyticsreporting.domain.exceptions;

/**
 * Exception thrown when attempting to create a report that already exists.
 */
public class DuplicateReportException extends RuntimeException {
    public DuplicateReportException(String message) {
        super(message);
    }
}
