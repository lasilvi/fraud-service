package com.fraud.application.usecase;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.application.port.out.FraudThresholdProvider;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import com.fraud.domain.model.Transaction;

class EvaluateTransactionUseCaseTest {

    @Test
    void shouldEvaluateAndPersistWhenRuleIsTriggered() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort);

        Transaction transaction = new Transaction("test-id-1", BigDecimal.valueOf(20000), "US", "US", "192.168.1.1", java.time.Instant.now());
        FraudEvaluationResult result = useCase.execute(transaction);

        assertTrue(result.suspicious());
        assertEquals(RiskLevel.MEDIUM, result.riskLevel());
        assertTrue(result.reasons().contains(FraudReason.HIGH_AMOUNT));
        assertNotNull(auditPort.savedResult);
        assertEquals(transaction, auditPort.savedTransaction);
    }

    @Test
    void shouldEvaluateLowRiskAndPersistWhenNoRuleIsTriggered() {
        FraudThresholdProvider thresholdProvider = () -> BigDecimal.valueOf(15000);
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(thresholdProvider, auditPort);

        Transaction transaction = new Transaction("test-id-2", BigDecimal.valueOf(3000), "CO", "CO", "192.168.1.2", java.time.Instant.now());
        FraudEvaluationResult result = useCase.execute(transaction);

        assertTrue(result.reasons().isEmpty());
        assertEquals(RiskLevel.LOW, result.riskLevel());
        assertNotNull(auditPort.savedResult);
    }

    private static final class InMemoryAuditPort implements FraudEvaluationAuditPort {

        private Transaction savedTransaction;
        private FraudEvaluationResult savedResult;

        @Override
        public void save(Transaction transaction, FraudEvaluationResult result) {
            this.savedTransaction = transaction;
            this.savedResult = result;
        }
    }
}
