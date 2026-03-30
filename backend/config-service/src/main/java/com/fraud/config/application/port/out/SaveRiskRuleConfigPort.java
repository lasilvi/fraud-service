package com.fraud.config.application.port.out;

import com.fraud.config.domain.model.RiskRuleConfig;

public interface SaveRiskRuleConfigPort {
	RiskRuleConfig save(RiskRuleConfig config);
}
