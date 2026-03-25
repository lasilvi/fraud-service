package com.fraud.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fraud.application.port.out.FraudEvaluationAuditPort;
import com.fraud.domain.model.FraudEvaluationResult;
import com.fraud.domain.model.FraudReason;
import com.fraud.domain.model.RiskLevel;
import com.fraud.domain.model.Transaction;
import com.fraud.infrastructure.config.FraudProperties;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class EvaluateTransactionUseCaseTest {

    @Test
    void shouldEvaluateAndPersistWhenRuleIsTriggered() {
        FraudProperties properties = new FraudProperties();
        properties.setThreshold(BigDecimal.valueOf(15000));
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(properties, auditPort);

        Transaction transaction = new Transaction(BigDecimal.valueOf(20000), "US", "US");
        FraudEvaluationResult result = useCase.execute(transaction);

        assertTrue(result.suspicious());
        assertEquals(RiskLevel.MEDIUM, result.riskLevel());
        assertTrue(result.reasons().contains(FraudReason.HIGH_AMOUNT));
        assertNotNull(auditPort.savedResult);
        assertEquals(transaction, auditPort.savedTransaction);
    }

    @Test
    void shouldEvaluateLowRiskAndPersistWhenNoRuleIsTriggered() {
        FraudProperties properties = new FraudProperties();
        properties.setThreshold(BigDecimal.valueOf(15000));
        InMemoryAuditPort auditPort = new InMemoryAuditPort();
        EvaluateTransactionUseCase useCase = new EvaluateTransactionUseCase(properties, auditPort);

        Transaction transaction = new Transaction(BigDecimal.valueOf(3000), "CO", "CO");
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
