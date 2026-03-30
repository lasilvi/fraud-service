package com.fraud.config.infrastructure.controller;

import com.fraud.config.application.usecase.CreateRiskRuleUseCase;
import com.fraud.config.application.usecase.DeleteRiskRuleUseCase;
import com.fraud.config.application.usecase.GetAllRiskRulesUseCase;
import com.fraud.config.application.usecase.GetThresholdUseCase;
import com.fraud.config.application.usecase.GetUserLocationUseCase;
import com.fraud.config.application.usecase.SaveUserLocationUseCase;
import com.fraud.config.application.usecase.UpdateThresholdUseCase;
import com.fraud.config.domain.model.RiskLevel;
import com.fraud.config.domain.model.RiskRuleConfig;
import com.fraud.config.domain.model.ThresholdConfig;
import com.fraud.config.domain.model.UserLocationConfig;
import com.fraud.config.infrastructure.controller.dto.RiskRuleConfigDTO;
import com.fraud.config.infrastructure.controller.dto.ThresholdConfigDTO;
import com.fraud.config.infrastructure.controller.dto.UserLocationConfigDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConfigControllerTest {

	private UpdateThresholdUseCase updateThresholdUseCase;
	private GetThresholdUseCase getThresholdUseCase;
	private SaveUserLocationUseCase saveUserLocationUseCase;
	private GetUserLocationUseCase getUserLocationUseCase;
	private CreateRiskRuleUseCase createRiskRuleUseCase;
	private GetAllRiskRulesUseCase getAllRiskRulesUseCase;
	private DeleteRiskRuleUseCase deleteRiskRuleUseCase;
	private ConfigController controller;

	@BeforeEach
	void setUp() {
		updateThresholdUseCase = mock(UpdateThresholdUseCase.class);
		getThresholdUseCase = mock(GetThresholdUseCase.class);
		saveUserLocationUseCase = mock(SaveUserLocationUseCase.class);
		getUserLocationUseCase = mock(GetUserLocationUseCase.class);
		createRiskRuleUseCase = mock(CreateRiskRuleUseCase.class);
		getAllRiskRulesUseCase = mock(GetAllRiskRulesUseCase.class);
		deleteRiskRuleUseCase = mock(DeleteRiskRuleUseCase.class);

		controller = new ConfigController(
			updateThresholdUseCase,
			getThresholdUseCase,
			saveUserLocationUseCase,
			getUserLocationUseCase,
			createRiskRuleUseCase,
			getAllRiskRulesUseCase,
			deleteRiskRuleUseCase
		);
	}

	// --- Threshold ---

	@Test
	void shouldUpdateThreshold() {
		ThresholdConfigDTO dto = new ThresholdConfigDTO(new BigDecimal("18000"));

		ResponseEntity<Void> response = controller.updateThreshold(dto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(updateThresholdUseCase).execute(any(ThresholdConfig.class));
	}

	@Test
	void shouldGetCurrentThreshold() {
		when(getThresholdUseCase.execute()).thenReturn(new ThresholdConfig(new BigDecimal("15000")));

		ResponseEntity<ThresholdConfigDTO> response = controller.getThreshold();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(new BigDecimal("15000"), response.getBody().threshold());
	}

	// --- User Location ---

	@Test
	void shouldSaveUserLocation() {
		UserLocationConfigDTO dto = new UserLocationConfigDTO("user123", "CO");

		ResponseEntity<Void> response = controller.saveUserLocation(dto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(saveUserLocationUseCase).execute(any(UserLocationConfig.class));
	}

	@Test
	void shouldGetUserLocationWhenExists() {
		when(getUserLocationUseCase.execute("user123"))
			.thenReturn(Optional.of(new UserLocationConfig("user123", "CO")));

		ResponseEntity<UserLocationConfigDTO> response = controller.getUserLocation("user123");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("CO", response.getBody().usualCountry());
	}

	@Test
	void shouldReturn404WhenUserLocationNotFound() {
		when(getUserLocationUseCase.execute("unknown")).thenReturn(Optional.empty());

		ResponseEntity<UserLocationConfigDTO> response = controller.getUserLocation("unknown");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	// --- Risk Rules ---

	@Test
	void shouldCreateRiskRule() {
		RiskRuleConfigDTO dto = new RiskRuleConfigDTO(null, "amount > 10000", RiskLevel.HIGH);
		when(createRiskRuleUseCase.execute(any(RiskRuleConfig.class)))
			.thenReturn(new RiskRuleConfig(1L, "amount > 10000", RiskLevel.HIGH));

		ResponseEntity<RiskRuleConfigDTO> response = controller.createRiskRule(dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1L, response.getBody().id());
	}

	@Test
	void shouldGetAllRiskRules() {
		when(getAllRiskRulesUseCase.execute()).thenReturn(List.of(
			new RiskRuleConfig(1L, "amount > 10000", RiskLevel.HIGH),
			new RiskRuleConfig(2L, "amount > 5000", RiskLevel.MEDIUM)
		));

		ResponseEntity<List<RiskRuleConfigDTO>> response = controller.getAllRiskRules();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	void shouldDeleteRiskRule() {
		ResponseEntity<Void> response = controller.deleteRiskRule(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(deleteRiskRuleUseCase).execute(1L);
	}
}
