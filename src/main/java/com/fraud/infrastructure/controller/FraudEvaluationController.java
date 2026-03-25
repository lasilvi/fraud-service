package com.fraud.infrastructure.controller;

import com.fraud.application.usecase.EvaluateTransactionUseCase;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.infrastructure.controller.dto.FraudEvaluationRequest;
import com.fraud.infrastructure.controller.dto.FraudEvaluationResponse;
import com.fraud.infrastructure.controller.mapper.FraudEvaluationRequestMapper;
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
	private final FraudEvaluationRequestMapper requestMapper;

	public FraudEvaluationController(
		EvaluateTransactionUseCase evaluateTransactionUseCase,
		FraudEvaluationRequestMapper requestMapper
	) {
		this.evaluateTransactionUseCase = evaluateTransactionUseCase;
		this.requestMapper = requestMapper;
	}

	@PostMapping("/evaluate")
	public ResponseEntity<FraudEvaluationResponse> evaluate(@RequestBody FraudEvaluationRequest request) {
		FraudEvaluationResult result = evaluateTransactionUseCase.execute(requestMapper.toDomain(request));

		return ResponseEntity.ok(FraudEvaluationResponse.fromDomain(result));
	}

	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> health() {
		return ResponseEntity.ok(Map.of("status", "UP"));
	}
}
