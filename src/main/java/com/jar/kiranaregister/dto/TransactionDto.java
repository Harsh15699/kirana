package com.jar.kiranaregister.dto;

import com.jar.kiranaregister.model.enums.CurrencyType;
import com.jar.kiranaregister.model.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionDto {

	private double amount;
	
	private CurrencyType currencyType;
	
	private TransactionType transactionType;

}
