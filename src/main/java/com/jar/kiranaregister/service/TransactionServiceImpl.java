package com.jar.kiranaregister.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jar.kiranaregister.dto.request.RecordTransactionDto;
import com.jar.kiranaregister.dto.response.RecordedTransactionDto;
import com.jar.kiranaregister.dto.response.TransactionRetrievalDto;
import com.jar.kiranaregister.dto.response.TransactionSummaryDto;
import com.jar.kiranaregister.exception.InvalidDateException;
import com.jar.kiranaregister.exception.NegativeAmountException;
import com.jar.kiranaregister.model.KiranaTransaction;
import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;
import com.jar.kiranaregister.repository.MySqlTransactionRepository;
import com.jar.kiranaregister.repository.TransactionRepository;
import com.jar.kiranaregister.util.CommonUtil;
import com.jar.kiranaregister.util.CurrencyConverterUtil;
import com.jar.kiranaregister.util.DateUtil;

/**
 * Service implementation for handling transactions.
 * Implements the TransactionService interface to provide transaction-related functionalities.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final MySqlTransactionRepository mySqlTransactionRepository;
    
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, MySqlTransactionRepository mySqlTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.mySqlTransactionRepository=mySqlTransactionRepository;
    }

    /**
     * Records a transaction based on the provided DTO.
     *
     * @param recordTransactionDto The DTO containing details of the transaction to be recorded.
     * @return The DTO representing the recorded transaction.
     */
    @Override
	public RecordedTransactionDto recordTransaction(RecordTransactionDto recordTransactionDto) {
    	
    	if (recordTransactionDto.getAmount() < 0) {
    	    throw new NegativeAmountException("The transaction amount should be a non-negative value. Please provide a valid positive amount.");
    	}

	    try {
	        // Convert USD to INR if necessary
	        Double amount = recordTransactionDto.getAmount();
	        
	        boolean isUSD = false;
	        if (recordTransactionDto.getCurrencyType().equals(CurrencyType.USD)) {
	            amount = CurrencyConverterUtil.convertDollarToRupee(amount);
	            isUSD = true;
	        }

	        // Build KiranaTransaction entity and save to the database
	        KiranaTransaction kiranaTransaction = KiranaTransaction.builder()
	                .amount(CommonUtil.convertToTwoDecimalPlaces(amount))
	                .currencyType(CurrencyType.INR)
	                .transactionType(recordTransactionDto.getTransactionType())
	                .build();
	        transactionRepository.save(kiranaTransaction);

	        // Convert KiranaTransaction to RecordedTransactionDto
	        RecordedTransactionDto recordedTransactionDto =
	                convertKiranaTransactionToAddTransactionResponseDto(kiranaTransaction, recordTransactionDto.getAmount(), isUSD);

	        return recordedTransactionDto;
	    } catch (DataAccessException e) {
	        throw new RuntimeException("Error recording transaction to the database. Please try again later.");
	    } catch (Exception e) {
	        throw new RuntimeException("Unexpected error recording transaction. Please try again later.");
	    }
    }


    /**
     * Retrieves a summary of transactions based on the specified date.
     *
     * @param dateString The date for which the transaction summary is requested.
     * @return The DTO representing the transaction summary.
     * @throws InvalidDateException If the provided date is invalid.
     */
    @Override
    public TransactionSummaryDto getAllTransactions(String dateString) throws InvalidDateException {
        // Retrieve KiranaTransactions based on the provided date
        List<KiranaTransaction> kiranaTransactions;
        if (dateString != null) {
            LocalDate date = DateUtil.stringToDate(dateString);
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
            kiranaTransactions = mySqlTransactionRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        } else {
            kiranaTransactions = transactionRepository.findAll();
        }

        // Process KiranaTransactions and generate summary
        List<TransactionRetrievalDto> transactionRetrievalDtos = new ArrayList<>();
        long totalNumberOfTransactions = 0;
        double totalDebitAmount = 0, totalCreditAmount = 0;
        for (KiranaTransaction kiranaTransaction : kiranaTransactions) {
            totalNumberOfTransactions++;
            if (kiranaTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                totalCreditAmount += kiranaTransaction.getAmount();
            } else {
                totalDebitAmount += kiranaTransaction.getAmount();
            }
            transactionRetrievalDtos.add(convertKiranaTransactionToTransactionResponseDto(kiranaTransaction));
        }

        // Build and return TransactionSummaryDto
        TransactionSummaryDto transactionSummaryDto = TransactionSummaryDto.builder()
                .totalCreditAmount(totalCreditAmount)
                .totalDebitAmount(totalDebitAmount)
                .netAmount(CommonUtil.convertToTwoDecimalPlaces(totalCreditAmount - totalDebitAmount))
                .totalNumberOfTransactions(totalNumberOfTransactions)
                .transactions(transactionRetrievalDtos)
                .build();

        return transactionSummaryDto;
    }

    /**
     * Converts a KiranaTransaction entity to a TransactionRetrievalDto.
     *
     * @param kiranaTransaction The KiranaTransaction entity to convert.
     * @return The DTO representing the transaction details.
     */
    private TransactionRetrievalDto convertKiranaTransactionToTransactionResponseDto(KiranaTransaction kiranaTransaction) {
        TransactionRetrievalDto transactionRetrievalDto = TransactionRetrievalDto.builder()
                .amount(kiranaTransaction.getAmount())
                .currencyType(kiranaTransaction.getCurrencyType())
                .transactionType(kiranaTransaction.getTransactionType())
                .createdAt(kiranaTransaction.getCreatedAt()!=null?kiranaTransaction.getCreatedAt().toLocalDate():null)
                .build();
        return transactionRetrievalDto;
    }

    /**
     * Converts a KiranaTransaction entity to a RecordedTransactionDto.
     *
     * @param kiranaTransaction The KiranaTransaction entity to convert.
     * @param initialAmount     The initial amount before any conversion.
     * @param isUSD             Indicates if the initial amount is in USD.
     * @return The DTO representing the recorded transaction details.
     */
    private RecordedTransactionDto convertKiranaTransactionToAddTransactionResponseDto(
            KiranaTransaction kiranaTransaction, double initialAmount, boolean isUSD) {
        RecordedTransactionDto recordedTransactionDto = RecordedTransactionDto.builder()
                .amount(initialAmount)
                .currencyType(kiranaTransaction.getCurrencyType())
                .transactionType(kiranaTransaction.getTransactionType())
                .createdAt(kiranaTransaction.getCreatedAt()!=null?kiranaTransaction.getCreatedAt().toLocalDate():null)
                .convertedAmount(isUSD ? kiranaTransaction.getAmount() : null)
                .build();
        return recordedTransactionDto;
    }
}
