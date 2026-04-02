package com.fraud.alert.infrastructure.controller;

import com.fraud.alert.application.usecase.GetAllAlertsUseCase;
import com.fraud.alert.domain.model.FraudAlert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final GetAllAlertsUseCase getAllAlertsUseCase;

    public AlertController(GetAllAlertsUseCase getAllAlertsUseCase) {
        this.getAllAlertsUseCase = getAllAlertsUseCase;
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<FraudAlert>> getAlerts() {
        return ResponseEntity.ok(getAllAlertsUseCase.execute());
    }
}
