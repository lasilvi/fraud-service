package com.fraud.alert.application.usecase;

import com.fraud.alert.application.port.out.GetAllFraudAlertsPort;
import com.fraud.alert.domain.model.FraudAlert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAlertsUseCase {

    private final GetAllFraudAlertsPort getAllPort;

    public GetAllAlertsUseCase(GetAllFraudAlertsPort getAllPort) {
        this.getAllPort = getAllPort;
    }

    public List<FraudAlert> execute() {
        return getAllPort.getAll();
    }
}
