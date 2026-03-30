package com.fraud.config.infrastructure.persistence;

import com.fraud.config.domain.model.RiskLevel;
import com.fraud.config.domain.model.RiskRuleConfig;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RiskRuleConfigAdapterTest {

	private RiskRuleConfigRepository repository;
	private RiskRuleConfigAdapter adapter;

	@BeforeEach
	void setUp() {
		repository = mock(RiskRuleConfigRepository.class);
		adapter = new RiskRuleConfigAdapter(repository);
	}

	@Test
	void shouldSaveRiskRuleConfig() {
		RiskRuleConfig input = new RiskRuleConfig(null, "amount > 10000", RiskLevel.HIGH);
		RiskRuleConfigEntity savedEntity = new RiskRuleConfigEntity();
		savedEntity.setId(1L);
		savedEntity.setCondition("amount > 10000");
		savedEntity.setRiskLevel(RiskLevel.HIGH);
		when(repository.save(any(RiskRuleConfigEntity.class))).thenReturn(savedEntity);

		RiskRuleConfig result = adapter.save(input);

		assertEquals(1L, result.id());
		assertEquals("amount > 10000", result.condition());
		assertEquals(RiskLevel.HIGH, result.riskLevel());
	}

	@Test
	void shouldGetAllRiskRules() {
		RiskRuleConfigEntity entity1 = new RiskRuleConfigEntity();
		entity1.setId(1L);
		entity1.setCondition("amount > 10000");
		entity1.setRiskLevel(RiskLevel.HIGH);

		RiskRuleConfigEntity entity2 = new RiskRuleConfigEntity();
		entity2.setId(2L);
		entity2.setCondition("amount > 5000");
		entity2.setRiskLevel(RiskLevel.MEDIUM);

		when(repository.findAll()).thenReturn(List.of(entity1, entity2));

		List<RiskRuleConfig> result = adapter.getAll();

		assertEquals(2, result.size());
	}

	@Test
	void shouldDeleteRiskRule() {
		adapter.delete(1L);

		verify(repository, times(1)).deleteById(1L);
	}
}
