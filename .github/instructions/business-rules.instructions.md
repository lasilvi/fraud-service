---
applyTo: "backend/**"
description: "Fraud detection business rules and domain constraints. Use when implementing or modifying fraud evaluation logic."
---

# Fraud Detection Business Rules

## Transaction Evaluation

- **Threshold**: configurable via `application.yml` (`fraud.threshold`), default `15000`
- A transaction is **suspicious** if ANY rule triggers
- Current rules:
  - `AmountRule`: `amount > threshold` → `FraudReason.HIGH_AMOUNT`
  - `LocationRule`: `transactionCountry != userCountry` → `FraudReason.UNUSUAL_LOCATION`

## Risk Classification

| Triggered rules | Risk Level |
|-----------------|------------|
| 0               | `LOW`      |
| 1               | `MEDIUM`   |
| 2+              | `HIGH`     |

## Domain Constraints

- Amounts use `BigDecimal` (precision for financial operations)
- Negative amounts are rejected (`IllegalArgumentException`)
- Rules implement `FraudRule` interface — adding new rules requires no changes to the orchestrator
- `FraudEvaluationResult` is an immutable record: `suspicious`, `riskLevel`, `reasons`
