package com.jar.kiranaregister.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonPropertyOrder({"amount", "convertedAmount"})
public class RecordedTransactionDto extends TransactionRetrievalDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double convertedAmount;
}
