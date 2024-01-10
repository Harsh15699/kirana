package com.jar.kiranaregister.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jar.kiranaregister.model.KiranaTransaction;

/**
 * Custom Spring Data JPA repository for accessing KiranaTransaction entities in the MySQL database.
 * Extends TransactionRepository, inheriting common CRUD operations and adds a custom query method.
 */
@Repository
public interface MySqlTransactionRepository extends TransactionRepository {

    /**
     * Retrieves a list of KiranaTransaction entities created between the specified start and end dates.
     *
     * @param startOfDay The start date and time of the range.
     * @param endOfDay   The end date and time of the range.
     * @return A list of KiranaTransaction entities created within the specified date range.
     */
    List<KiranaTransaction> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
