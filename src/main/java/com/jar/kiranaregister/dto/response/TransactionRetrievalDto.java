package com.jar.kiranaregister.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jar.kiranaregister.dto.TransactionDto;

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
public class TransactionRetrievalDto extends TransactionDto{
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDate createdAt;
}
