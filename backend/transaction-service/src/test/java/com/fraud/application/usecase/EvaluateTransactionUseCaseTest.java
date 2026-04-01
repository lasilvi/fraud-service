package com.fraud.application.usecase;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.application.port.out.FraudThresholdProvider;
import com.fraud.application.port.out.SaveTransactionPort;
import com.fraud.application.port.out.UserLocationProvider;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import com.fraud.domain.model.Transaction;

class EvaluateTransactionUseCaseTest {

    @Test
    void shouldEvaluateAndPersistWhenRuleIsTriggered() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        InMemoryTransactionPort transactionPort = new InMemoryTransactionPort();
        UserLocationProvider locationProvider = userId -> Optional.empty();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort, transactionPort, locationProvider);

        Transaction transaction = new Transaction("test-id-1", BigDecimal.valueOf(20000), "US", "192.168.1.1", java.time.Instant.now(), "user1");
        FraudEvaluationResult result = useCase.execute(transaction);

        assertTrue(result.suspicious());
        assertEquals(RiskLevel.MEDIUM, result.riskLevel());
        assertTrue(result.reasons().contains(FraudReason.HIGH_AMOUNT));
        assertNotNull(auditPort.savedResult);
        assertEquals(transaction, auditPort.savedTransaction);
        assertEquals(transaction, transactionPort.savedTransaction);
    }

    @Test
    void shouldEvaluateLowRiskAndPersistWhenNoRuleIsTriggered() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        InMemoryTransactionPort transactionPort = new InMemoryTransactionPort();
        UserLocationProvider locationProvider = userId -> Optional.empty();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort, transactionPort, locationProvider);

        Transaction transaction = new Transaction("test-id-2", BigDecimal.valueOf(3000), "CO", "192.168.1.2", java.time.Instant.now(), "user2");
        FraudEvaluationResult result = useCase.execute(transaction);

        assertTrue(result.reasons().isEmpty());
        assertEquals(RiskLevel.LOW, result.riskLevel());
        assertNotNull(auditPort.savedResult);
    }

    @Test
    void shouldUseUsualCountryFromConfigService_whenAvailable() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        InMemoryTransactionPort transactionPort = new InMemoryTransactionPort();
        // Config-service says user123's usual country is US
        UserLocationProvider locationProvider = userId -> Optional.of("US");
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort, transactionPort, locationProvider);

        // Config says US. Transaction is in US → no UNUSUAL_LOCATION
        Transaction transaction = new Transaction("test-id-3", BigDecimal.valueOf(3000), "US", "192.168.1.3", java.time.Instant.now(), "user123");
        FraudEvaluationResult result = useCase.execute(transaction);

        assertFalse(result.suspicious());
        assertEquals(RiskLevel.LOW, result.riskLevel());
        assertFalse(result.reasons().contains(FraudReason.UNUSUAL_LOCATION));
    }

    @Test
    void shouldSkipLocationRule_whenUsualCountryNotConfigured() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        InMemoryTransactionPort transactionPort = new InMemoryTransactionPort();
        // Config-service returns empty for this user
        UserLocationProvider locationProvider = userId -> Optional.empty();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort, transactionPort, locationProvider);

        // No config for user → LocationRule is skipped, only AmountRule applies (3000 < 15000 → LOW)
        Transaction transaction = new Transaction("test-id-4", BigDecimal.valueOf(3000), "US", "192.168.1.4", java.time.Instant.now(), "user456");
        FraudEvaluationResult result = useCase.execute(transaction);

        assertFalse(result.suspicious());
        assertEquals(RiskLevel.LOW, result.riskLevel());
        assertFalse(result.reasons().contains(FraudReason.UNUSUAL_LOCATION));
    }

    private static final class InMemoryAuditPort implements FraudEvaluationAuditPort {

        private Transaction savedTransaction;
        private FraudEvaluationResult savedResult;
        private String savedUserCountry;

        @Override
        public void save(Transaction transaction, FraudEvaluationResult result, String resolvedUserCountry) {
            this.savedTransaction = transaction;
            this.savedResult = result;
            this.savedUserCountry = resolvedUserCountry;
        }
    }

    private static final class InMemoryTransactionPort implements SaveTransactionPort {

        private Transaction savedTransaction;

        @Override
        public void save(Transaction transaction) {
            this.savedTransaction = transaction;
        }
    }
}
