package com.fraud.alert.infrastructure.persistence;

import com.fraud.alert.application.port.out.GetAllFraudAlertsPort;
import com.fraud.alert.application.port.out.SaveFraudAlertPort;
import com.fraud.alert.domain.model.FraudAlert;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class FraudAlertPersistenceAdapter implements SaveFraudAlertPort, GetAllFraudAlertsPort {

    private final FraudAlertJpaRepository repository;

    public FraudAlertPersistenceAdapter(FraudAlertJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public FraudAlert save(FraudAlert alert) {
        FraudAlertEntity entity = new FraudAlertEntity();
        entity.setUserId(alert.userId());
        entity.setAmount(alert.amount());
        entity.setRiskLevel(alert.riskLevel());
        entity.setReasons(String.join(",", alert.reasons()));
        entity.setTimestamp(alert.timestamp());
        FraudAlertEntity saved = repository.save(entity);
        return toFraudAlert(saved);
    }

    @Override
    public List<FraudAlert> getAll() {
        return repository.findAllByOrderByTimestampDesc().stream()
                .map(this::toFraudAlert)
                .toList();
    }

    private FraudAlert toFraudAlert(FraudAlertEntity entity) {
        List<String> reasons = entity.getReasons() == null || entity.getReasons().isBlank()
                ? Collections.emptyList()
                : Arrays.asList(entity.getReasons().split(","));
        return new FraudAlert(entity.getId(), entity.getUserId(), entity.getAmount(),
                entity.getRiskLevel(), reasons, entity.getTimestamp());
    }
}
