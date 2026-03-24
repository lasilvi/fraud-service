package com.fraud.infrastructure.controller;

import com.fraud.application.usecase.EvaluateTransactionUseCase;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.Transaction;
import com.fraud.infrastructure.controller.dto.FraudEvaluationRequest;
import com.fraud.infrastructure.controller.dto.FraudEvaluationResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fraud")
public class FraudEvaluationController {

	private final EvaluateTransactionUseCase evaluateTransactionUseCase;

	public FraudEvaluationController(EvaluateTransactionUseCase evaluateTransactionUseCase) {
		this.evaluateTransactionUseCase = evaluateTransactionUseCase;
	}

	@PostMapping("/evaluate")
	public ResponseEntity<FraudEvaluationResponse> evaluate(@RequestBody FraudEvaluationRequest request) {
		Transaction transaction = new Transaction(request.amount(), request.transactionCountry(), request.userCountry());
		FraudEvaluationResult result = evaluateTransactionUseCase.execute(transaction);

		return ResponseEntity.ok(FraudEvaluationResponse.fromDomain(result));
	}

	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> health() {
		return ResponseEntity.ok(Map.of("status", "UP"));
	}
}
