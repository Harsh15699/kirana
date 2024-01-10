package com.jar.kiranaregister.model;

import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;

/**
 * Interface representing a financial transaction.
 * Any class implementing this interface should provide methods to access
 * essential details of the transaction, such as its ID, amount, currency type,
 * and transaction type.
 */
public interface Transaction {

    /**
     * Retrieves the unique identifier of the transaction.
     *
     * @return The unique identifier of the transaction.
     */
    long getId();

    /**
     * Retrieves the amount of the transaction.
     *
     * @return The amount of the transaction.
     */
    double getAmount();

    /**
     * Retrieves the currency type of the transaction.
     *
     * @return The currency type of the transaction.
     */
    CurrencyType getCurrencyType();

    /**
     * Retrieves the transaction type (e.g., credit, debit).
     *
     * @return The transaction type.
     */
    TransactionType getTransactionType();
}
