package com.stocksip.analyticsreporting.interfaces.rest;

import com.stocksip.analyticsreporting.domain.exceptions.DuplicateReportException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @summary
 * Global exception handler for the application.
 * Provides consistent error responses for different types of exceptions.
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles DuplicateReportException and returns a 409 Conflict response.
     * @param ex the DuplicateReportException to handle
     * @param request the WebRequest object
     * @return ResponseEntity with the error details
     */
    @ExceptionHandler(DuplicateReportException.class)
    public ResponseEntity<Object> handleDuplicateReportException(
            DuplicateReportException ex, WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Handles IllegalArgumentException and returns a 400 Bad Request response.
     * @param ex the IllegalArgumentException to handle
     * @param request the WebRequest object
     * @return ResponseEntity with the error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
