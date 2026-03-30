package com.fraud.config.application.port.out;

import java.util.List;

import com.fraud.config.domain.model.RiskRuleConfig;

public interface GetAllRiskRulesConfigPort {
	List<RiskRuleConfig> getAll();
}
