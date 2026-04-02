package com.fraud.alert.application.port.out;

import com.fraud.alert.domain.model.FraudAlert;

import java.util.List;

public interface GetAllFraudAlertsPort {
    List<FraudAlert> getAll();
}
