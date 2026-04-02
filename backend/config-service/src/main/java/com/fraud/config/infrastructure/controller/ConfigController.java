package com.fraud.config.infrastructure.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fraud.config.application.usecase.CreateRiskRuleUseCase;
import com.fraud.config.application.usecase.DeleteRiskRuleUseCase;
import com.fraud.config.application.usecase.GetAllRiskRulesUseCase;
import com.fraud.config.application.usecase.GetThresholdUseCase;
import com.fraud.config.application.usecase.GetUserLocationUseCase;
import com.fraud.config.application.usecase.SaveUserLocationUseCase;
import com.fraud.config.application.usecase.UpdateThresholdUseCase;
import com.fraud.config.domain.model.RiskRuleConfig;
import com.fraud.config.domain.model.ThresholdConfig;
import com.fraud.config.domain.model.UserLocationConfig;
import com.fraud.config.infrastructure.controller.dto.RiskRuleConfigDTO;
import com.fraud.config.infrastructure.controller.dto.ThresholdConfigDTO;
import com.fraud.config.infrastructure.controller.dto.UserLocationConfigDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

	private final UpdateThresholdUseCase updateThresholdUseCase;
	private final GetThresholdUseCase getThresholdUseCase;
	private final SaveUserLocationUseCase saveUserLocationUseCase;
	private final GetUserLocationUseCase getUserLocationUseCase;
	private final CreateRiskRuleUseCase createRiskRuleUseCase;
	private final GetAllRiskRulesUseCase getAllRiskRulesUseCase;
	private final DeleteRiskRuleUseCase deleteRiskRuleUseCase;

	public ConfigController(
		UpdateThresholdUseCase updateThresholdUseCase,
		GetThresholdUseCase getThresholdUseCase,
		SaveUserLocationUseCase saveUserLocationUseCase,
		GetUserLocationUseCase getUserLocationUseCase,
		CreateRiskRuleUseCase createRiskRuleUseCase,
		GetAllRiskRulesUseCase getAllRiskRulesUseCase,
		DeleteRiskRuleUseCase deleteRiskRuleUseCase
	) {
		this.updateThresholdUseCase = updateThresholdUseCase;
		this.getThresholdUseCase = getThresholdUseCase;
		this.saveUserLocationUseCase = saveUserLocationUseCase;
		this.getUserLocationUseCase = getUserLocationUseCase;
		this.createRiskRuleUseCase = createRiskRuleUseCase;
		this.getAllRiskRulesUseCase = getAllRiskRulesUseCase;
		this.deleteRiskRuleUseCase = deleteRiskRuleUseCase;
	}

	// --- Threshold ---

	@PutMapping("/threshold")
	public ResponseEntity<Void> updateThreshold(@Valid @RequestBody ThresholdConfigDTO dto) {
		updateThresholdUseCase.execute(new ThresholdConfig(dto.threshold()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/threshold")
	public ResponseEntity<ThresholdConfigDTO> getThreshold() {
		ThresholdConfig config = getThresholdUseCase.execute();
		return ResponseEntity.ok(new ThresholdConfigDTO(config.threshold()));
	}

	// --- User Location ---

	@PutMapping("/user-location")
	public ResponseEntity<Void> saveUserLocation(@Valid @RequestBody UserLocationConfigDTO dto) {
		saveUserLocationUseCase.execute(new UserLocationConfig(dto.userId(), dto.usualCountry()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user-location/{userId}")
	public ResponseEntity<UserLocationConfigDTO> getUserLocation(@PathVariable("userId") String userId) {
		return getUserLocationUseCase.execute(userId)
			.map(config -> ResponseEntity.ok(new UserLocationConfigDTO(config.userId(), config.usualCountry())))
			.orElse(ResponseEntity.notFound().build());
	}

	// --- Risk Rules ---

	@PostMapping("/risk-rules")
	public ResponseEntity<RiskRuleConfigDTO> createRiskRule(@Valid @RequestBody RiskRuleConfigDTO dto) {
		RiskRuleConfig created = createRiskRuleUseCase.execute(
			new RiskRuleConfig(null, dto.condition(), dto.riskLevel())
		);
		return ResponseEntity.created(URI.create("/api/v1/config/risk-rules/" + created.id()))
			.body(new RiskRuleConfigDTO(created.id(), created.condition(), created.riskLevel()));
	}

	@GetMapping("/risk-rules")
	public ResponseEntity<List<RiskRuleConfigDTO>> getAllRiskRules() {
		List<RiskRuleConfigDTO> rules = getAllRiskRulesUseCase.execute().stream()
			.map(r -> new RiskRuleConfigDTO(r.id(), r.condition(), r.riskLevel()))
			.toList();
		return ResponseEntity.ok(rules);
	}

	@DeleteMapping("/risk-rules/{id}")
	public ResponseEntity<Void> deleteRiskRule(@PathVariable("id") Long id) {
		deleteRiskRuleUseCase.execute(id);
		return ResponseEntity.noContent().build();
	}

	// --- Health ---

	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> health() {
		return ResponseEntity.ok(Map.of("status", "UP"));
	}
}
