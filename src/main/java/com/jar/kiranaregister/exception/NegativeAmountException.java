package com.jar.kiranaregister.exception;

/**
 * Exception thrown when a negative amount is encountered in a transaction.
 * This exception signals that the transaction amount should be a non-negative value.
 */
public class NegativeAmountException extends RuntimeException {

    /**
     * Constructs a new NegativeAmountException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public NegativeAmountException(String message) {
        super(message);
    }
}
