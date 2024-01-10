package com.jar.kiranaregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jar.kiranaregister.dto.request.RecordTransactionDto;
import com.jar.kiranaregister.dto.response.RecordedTransactionDto;
import com.jar.kiranaregister.dto.response.TransactionSummaryDto;
import com.jar.kiranaregister.exception.InvalidDateException;
import com.jar.kiranaregister.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Record a new transaction.
     *
     * @param recordTransactionDto The request payload containing transaction details.
     * @return ResponseEntity with RecordedTransactionDto and HTTP status OK.
     */
    @PostMapping
    public ResponseEntity<?> recordTransaction(
            @RequestBody RecordTransactionDto recordTransactionDto) {
    	RecordedTransactionDto recordedTransactionDto = null;
    	try {
    		recordedTransactionDto = transactionService.recordTransaction(recordTransactionDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
        
        return ResponseEntity.status(HttpStatus.CREATED).body(recordedTransactionDto);
    }

    /**
     * Retrieve all transactions, optionally filtered by date.
     *
     * @param dateString The optional parameter to filter transactions by date.
     * @return ResponseEntity with TransactionSummaryDto and HTTP status OK, or
     *         ResponseEntity with error message and HTTP status BAD_REQUEST if date is invalid.
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions(
            @RequestParam(name = "date", required = false) String dateString) {
        TransactionSummaryDto transactionSummaryDto;
        try {
            transactionSummaryDto = transactionService.getAllTransactions(dateString);
        } catch (InvalidDateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionSummaryDto);
    }
}
