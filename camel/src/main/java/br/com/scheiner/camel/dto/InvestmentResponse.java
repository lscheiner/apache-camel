package br.com.scheiner.camel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestmentResponse {
	
	
	public InvestmentResponse(String id) {
		super();
		this.id = id;
	}

	public InvestmentResponse() {
		super();
	}

	private String id;
	
}