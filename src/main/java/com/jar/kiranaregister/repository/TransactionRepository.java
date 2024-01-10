package com.jar.kiranaregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jar.kiranaregister.model.KiranaTransaction;

/**
 * Spring Data JPA repository for accessing KiranaTransaction entities in the database.
 * This repository extends JpaRepository, providing CRUD operations and query methods
 * for the KiranaTransaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<KiranaTransaction, Long> {
	
}
