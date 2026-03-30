package com.fraud.config.application.usecase;

import org.springframework.stereotype.Service;

import com.fraud.config.application.port.out.DeleteRiskRuleConfigPort;

@Service
public class DeleteRiskRuleUseCase {

	private final DeleteRiskRuleConfigPort deletePort;

	public DeleteRiskRuleUseCase(DeleteRiskRuleConfigPort deletePort) {
		this.deletePort = deletePort;
	}

	public void execute(Long id) {
		deletePort.delete(id);
	}
}
