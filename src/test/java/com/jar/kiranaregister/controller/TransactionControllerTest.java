package com.jar.kiranaregister.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jar.kiranaregister.dto.request.RecordTransactionDto;
import com.jar.kiranaregister.dto.response.RecordedTransactionDto;
import com.jar.kiranaregister.dto.response.TransactionSummaryDto;
import com.jar.kiranaregister.exception.InvalidDateException;
import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;
import com.jar.kiranaregister.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TransactionService transactionService; 

    @Test
    public void testRecordTransactionController_SuccessfulTransaction() throws Exception {
        // Arrange
        RecordTransactionDto requestDto = new RecordTransactionDto();
        // Set requestDto properties
        requestDto.setAmount(100.0); // Set an appropriate amount
        requestDto.setCurrencyType(CurrencyType.USD); // Set the currency type
        requestDto.setTransactionType(TransactionType.CREDIT);

        when(transactionService.recordTransaction(any())).thenReturn(new RecordedTransactionDto());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(requestDto.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.convertedAmount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyType").value(CurrencyType.INR.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").value(requestDto.getTransactionType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").exists());
    }

    @Test
    public void testGetAllTransactions_ValidDate() throws Exception {
        // Arrange
        String validDateString = "2024-01-10";
        when(transactionService.getAllTransactions(validDateString)).thenReturn(new TransactionSummaryDto(/* mock data here */));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions")
                .param("date", validDateString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalNumberOfTransactions").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCreditAmount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalDebitAmount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.netAmount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions").exists());
    }

    @Test
    public void testGetAllTransactions_InvalidDate() throws Exception {
        // Arrange
        String invalidDateString = "invalid-date";
        when(transactionService.getAllTransactions(invalidDateString)).thenThrow(new InvalidDateException("Invalid date"));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions")
                .param("date", invalidDateString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
