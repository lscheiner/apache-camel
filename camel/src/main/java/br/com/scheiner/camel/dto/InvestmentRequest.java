package br.com.scheiner.camel.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InvestmentRequest {

	private Long productId;

	private BigDecimal value;
}