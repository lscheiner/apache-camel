package br.com.scheiner.restapi.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.scheiner.restapi.controller.dto.InvestmentRequest;
import br.com.scheiner.restapi.controller.dto.InvestmentResponse;

@RestController
@RequestMapping("/api/v1/accounts")
public class RestApiController {

	@PostMapping("/{accountId}/investment")
	public ResponseEntity<InvestmentResponse> invest(@PathVariable long accountId,
			@RequestBody InvestmentRequest investmentRequest) {
		
		System.out.println(investmentRequest);

		return ResponseEntity.ok().body(InvestmentResponse.builder().id(UUID.randomUUID().toString()).build());
	}

}
