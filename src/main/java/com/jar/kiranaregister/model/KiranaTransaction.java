package com.jar.kiranaregister.model;

import java.time.LocalDateTime;

import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Kirana store transaction.
 * This class is annotated with @Entity, indicating that instances of this class
 * are entities and can be persisted to a relational database.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiranaTransaction implements Transaction {

    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The amount associated with the transaction.
     */
    private double amount;

    /**
     * The currency type of the transaction.
     */
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    /**
     * The type of transaction (e.g., credit, debit).
     */
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    /**
     * The date and time when the transaction was created.
     * This field is automatically set before persisting the entity using @PrePersist.
     */
    private LocalDateTime createdAt;

    /**
     * Method annotated with @PrePersist to set the createdAt field before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
