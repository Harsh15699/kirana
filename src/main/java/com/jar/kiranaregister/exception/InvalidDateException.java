package com.jar.kiranaregister.exception;

/**
 * Custom exception class to represent an invalid date format or value.
 * This exception is thrown when attempting to process a date that does not conform
 * to the expected format or is not a valid date.
 */
public class InvalidDateException extends RuntimeException {

    /**
     * Constructs a new InvalidDateException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
