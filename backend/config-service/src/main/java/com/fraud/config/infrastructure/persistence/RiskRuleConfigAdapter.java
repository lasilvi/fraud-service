package com.fraud.config.infrastructure.persistence;

import com.fraud.config.application.port.out.DeleteRiskRuleConfigPort;
import com.fraud.config.application.port.out.GetAllRiskRulesConfigPort;
import com.fraud.config.application.port.out.SaveRiskRuleConfigPort;
import com.fraud.config.domain.model.RiskRuleConfig;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RiskRuleConfigAdapter implements SaveRiskRuleConfigPort, GetAllRiskRulesConfigPort, DeleteRiskRuleConfigPort {

	private final RiskRuleConfigRepository repository;

	public RiskRuleConfigAdapter(RiskRuleConfigRepository repository) {
		this.repository = repository;
	}

	@Override
	public RiskRuleConfig save(RiskRuleConfig config) {
		RiskRuleConfigEntity entity = new RiskRuleConfigEntity();
		entity.setCondition(config.condition());
		entity.setRiskLevel(config.riskLevel());
		RiskRuleConfigEntity saved = repository.save(entity);
		return new RiskRuleConfig(saved.getId(), saved.getCondition(), saved.getRiskLevel());
	}

	@Override
	public List<RiskRuleConfig> getAll() {
		return repository.findAll().stream()
			.map(entity -> new RiskRuleConfig(entity.getId(), entity.getCondition(), entity.getRiskLevel()))
			.toList();
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
