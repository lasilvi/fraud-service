package com.fraud.alert.application.port.out;

import com.fraud.alert.domain.model.FraudAlert;

public interface SaveFraudAlertPort {
    FraudAlert save(FraudAlert alert);
}
