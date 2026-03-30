package com.fraud.config.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.GetAllRiskRulesConfigPort;
import com.fraud.config.domain.model.RiskRuleConfig;

@Service
public class GetAllRiskRulesUseCase {

	private final GetAllRiskRulesConfigPort getPort;

	public GetAllRiskRulesUseCase(GetAllRiskRulesConfigPort getPort) {
		this.getPort = getPort;
	}

	public List<RiskRuleConfig> execute() {
		return getPort.getAll();
	}
}
