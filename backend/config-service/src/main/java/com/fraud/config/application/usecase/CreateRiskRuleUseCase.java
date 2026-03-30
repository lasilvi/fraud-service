package com.fraud.config.application.usecase;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.SaveRiskRuleConfigPort;
import com.fraud.config.domain.model.RiskRuleConfig;

@Service
public class CreateRiskRuleUseCase {

	private final SaveRiskRuleConfigPort savePort;

	public CreateRiskRuleUseCase(SaveRiskRuleConfigPort savePort) {
		this.savePort = savePort;
	}

	public RiskRuleConfig execute(RiskRuleConfig config) {
		return savePort.save(config);
	}
}
