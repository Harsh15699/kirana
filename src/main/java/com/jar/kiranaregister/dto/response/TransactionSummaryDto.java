package com.jar.kiranaregister.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionSummaryDto {
	Long totalNumberOfTransactions;
	
	Double totalCreditAmount;
	
	Double totalDebitAmount;
	
	Double netAmount;
	
	List<TransactionRetrievalDto> transactions;
}
