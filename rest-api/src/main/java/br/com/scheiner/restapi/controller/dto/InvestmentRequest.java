package br.com.scheiner.restapi.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InvestmentRequest {

	private Long productId;

	private BigDecimal value;
}