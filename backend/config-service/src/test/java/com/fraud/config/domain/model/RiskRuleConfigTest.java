package com.fraud.config.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RiskRuleConfigTest {

	@Test
	void shouldCreateValidRiskRuleConfig() {
		RiskRuleConfig config = new RiskRuleConfig(null, "amount > 10000", RiskLevel.HIGH);
		assertEquals("amount > 10000", config.condition());
		assertEquals(RiskLevel.HIGH, config.riskLevel());
	}

	@Test
	void shouldCreateRiskRuleConfigWithId() {
		RiskRuleConfig config = new RiskRuleConfig(1L, "amount > 10000", RiskLevel.HIGH);
		assertEquals(1L, config.id());
	}

	@Test
	void shouldThrowExceptionWhenConditionIsNull() {
		assertThrows(IllegalArgumentException.class,
			() -> new RiskRuleConfig(null, null, RiskLevel.HIGH));
	}

	@Test
	void shouldThrowExceptionWhenConditionIsBlank() {
		assertThrows(IllegalArgumentException.class,
			() -> new RiskRuleConfig(null, "  ", RiskLevel.HIGH));
	}

	@Test
	void shouldThrowExceptionWhenRiskLevelIsNull() {
		assertThrows(IllegalArgumentException.class,
			() -> new RiskRuleConfig(null, "amount > 10000", null));
	}
}
