package com.jar.kiranaregister.service;

import org.springframework.stereotype.Service;

import com.jar.kiranaregister.dto.request.RecordTransactionDto;
import com.jar.kiranaregister.dto.response.RecordedTransactionDto;
import com.jar.kiranaregister.dto.response.TransactionSummaryDto;

@Service
public interface TransactionService {
	RecordedTransactionDto recordTransaction(RecordTransactionDto recordTransactionDto);
	TransactionSummaryDto getAllTransactions(String dateString);
}
