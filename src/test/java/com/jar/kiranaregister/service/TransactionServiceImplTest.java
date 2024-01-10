package com.jar.kiranaregister.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.jar.kiranaregister.dto.request.RecordTransactionDto;
import com.jar.kiranaregister.dto.response.RecordedTransactionDto;
import com.jar.kiranaregister.dto.response.TransactionSummaryDto;
import com.jar.kiranaregister.exception.InvalidDateException;
import com.jar.kiranaregister.exception.NegativeAmountException;
import com.jar.kiranaregister.model.KiranaTransaction;
import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;
import com.jar.kiranaregister.repository.MySqlTransactionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private MySqlTransactionRepository mySqlTransactionRepository;

    @Test
    public void testRecordTransaction_SuccessfulTransaction_INR() {
        // Arrange
        RecordTransactionDto requestDto = new RecordTransactionDto();
        requestDto.setAmount(100.0);
        requestDto.setCurrencyType(CurrencyType.INR);
        requestDto.setTransactionType(TransactionType.CREDIT);

        // Mock repository save method
        when(mySqlTransactionRepository.save(any())).thenReturn(new KiranaTransaction());

        // Act
        RecordedTransactionDto resultDto = transactionService.recordTransaction(requestDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals(requestDto.getAmount(), resultDto.getAmount());
        assertEquals(requestDto.getCurrencyType(), resultDto.getCurrencyType());
        assertEquals(requestDto.getTransactionType(), resultDto.getTransactionType());
        assertNull(resultDto.getConvertedAmount());
    }
    
    @Test
    public void testRecordTransaction_SuccessfulTransaction_USD() {
        // Arrange
        RecordTransactionDto requestDto = new RecordTransactionDto();
        requestDto.setAmount(100.0);
        requestDto.setCurrencyType(CurrencyType.USD);
        requestDto.setTransactionType(TransactionType.CREDIT);

        // Mock repository save method
        when(mySqlTransactionRepository.save(any())).thenReturn(new KiranaTransaction());

        // Act
        RecordedTransactionDto resultDto = transactionService.recordTransaction(requestDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals(requestDto.getAmount(), resultDto.getAmount());
        assertEquals(CurrencyType.INR, resultDto.getCurrencyType());
        assertEquals(requestDto.getTransactionType(), resultDto.getTransactionType());
        assertNotNull(resultDto.getConvertedAmount());
    }

    @Test
    public void testRecordTransaction_InvalidInput() {
        // Arrange
        RecordTransactionDto requestDto = new RecordTransactionDto();
        requestDto.setAmount(-100.0); // Invalid amount
        requestDto.setCurrencyType(CurrencyType.INR);
        requestDto.setTransactionType(TransactionType.CREDIT);

        // Act and Assert
        assertThrows(NegativeAmountException.class, () -> transactionService.recordTransaction(requestDto));
    }

    @Test
    public void testGetAllTransactions_ByDate() throws InvalidDateException {
        // Arrange
        String dateString = "2024-01-09";
        LocalDate date = LocalDate.parse(dateString);

        // Mock repository method
        List<KiranaTransaction> mockTransactions = new ArrayList<>();
        when(mySqlTransactionRepository.findByCreatedAtBetween(any(), any())).thenReturn(mockTransactions);

        // Act
        TransactionSummaryDto resultDto = transactionService.getAllTransactions(dateString);

        // Assert
        assertNotNull(resultDto);
        assertEquals(0, resultDto.getTotalNumberOfTransactions());
        assertEquals(0.0, resultDto.getTotalCreditAmount());
        assertEquals(0.0, resultDto.getTotalDebitAmount());
        assertEquals(0.0, resultDto.getNetAmount());
        assertEquals(mockTransactions, resultDto.getTransactions());
    }

    @Test
    public void testGetAllTransactions_AllDates() throws InvalidDateException {
        // Arrange
        String dateString = null;

        // Mock repository method
        List<KiranaTransaction> mockTransactions = new ArrayList<>();
        when(mySqlTransactionRepository.findAll()).thenReturn(mockTransactions);

        // Act
        TransactionSummaryDto resultDto = transactionService.getAllTransactions(dateString);

        // Assert
        assertNotNull(resultDto);
        assertEquals(0, resultDto.getTotalNumberOfTransactions());
        assertEquals(0.0, resultDto.getTotalCreditAmount());
        assertEquals(0.0, resultDto.getTotalDebitAmount());
        assertEquals(0.0, resultDto.getNetAmount());
        assertEquals(mockTransactions, resultDto.getTransactions());
    }

    @Test
    public void testGetAllTransactions_InvalidDate() {
        // Arrange
        String dateString = "invalid-date";

        // Act and Assert
        assertThrows(InvalidDateException.class, () -> transactionService.getAllTransactions(dateString));
    }
}
