# Abordaje de DEV Tasks por Historia de Usuario

## Resumen de Estimación

| HU  | Descripción              | SP | Justificación                                                                                  |
|-----|--------------------------|----|-------------------------------------------------------------------------------------------------|
| HU1 | Evaluar monto            | 3  | Lógica simple de validación y comparación contra umbral. Baja complejidad y poca incertidumbre. |
| HU2 | Evaluar ubicación        | 5  | Requiere comparar con historial del usuario y definir qué es "inusual". Mayor incertidumbre.    |
| HU3 | Marcar sospechosa        | 3  | Orquesta resultados de otras HUs. No agrega lógica compleja nueva.                             |
| HU4 | Clasificar el riesgo     | 5  | Combina múltiples condiciones e introduce reglas de negocio adicionales.                        |

---

## HU1 – Evaluar monto de la transacción (3 SP)

### Enfoque de desarrollo

Se aplicó **TDD estricto (RED → GREEN → REFACTOR)** con commits atómicos para cada paso.

**1. Diseño de la entidad `Transaction`**  
Se modeló como un **Java Record** inmutable en la capa de dominio con los campos: `id`, `amount` (BigDecimal), `transactionCountry`, `userCountry`, `ip` y `timestamp`. Se eligió `BigDecimal` para el monto por precisión en operaciones financieras.

**2. Persistencia**  
Se creó la entidad JPA `TransactionJpaEntity` mapeada a la tabla `transactions`. La generación del esquema se delegó a Hibernate (`ddl-auto: update`). El adaptador `TransactionPersistenceAdapter` implementa el puerto `SaveTransactionPort`, siguiendo arquitectura hexagonal.

**3. Comparación contra umbral**  
Se implementó la clase `AmountRule` que implementa la interfaz `FraudRule`. Compara el monto contra un umbral configurable (por defecto **15000**, definido en `application.yml` bajo `fraud.threshold`). Usa `BigDecimal.compareTo()` para comparación precisa.

**4. Determinación normal/inusual**  
Si `amount > threshold`, la regla retorna `FraudReason.HIGH_AMOUNT`; de lo contrario, retorna vacío (monto normal).

**5. Manejo de errores**  
- Montos negativos → `IllegalArgumentException` lanzada desde `AmountRule`.
- Valores nulos → validación explícita de `amount` y `threshold`.
- En la capa de entrada: validación con Jakarta (`@DecimalMin("0")`) en el DTO `FraudEvaluationRequest`.

### Tests implementados
- `AmountRuleTest`: monto superior, inferior e igual al umbral; excepción por monto negativo.

---

## HU2 – Evaluar ubicación de la transacción (5 SP)

### Enfoque de desarrollo

**1. Datos de ubicación en la entidad**  
La entidad `Transaction` ya incluye los campos `transactionCountry` (país de la transacción) y `userCountry` (país registrado del usuario), más `ip`. No se creó entidad separada; la ubicación viaja como atributo de la transacción.

**2. Persistencia del país**  
Los datos de país se persisten dentro de `TransactionJpaEntity` y `FraudEvaluationJpaEntity`. No se requirió tabla adicional; el país se almacena como campo `String` en ambas tablas.

**3. Comparación de países**  
Se implementó `LocationRule` en la capa de dominio. Compara `transactionCountry` con `userCountry` mediante igualdad de cadenas.

**4. Detección de ubicación inusual**  
Si los países difieren → retorna `FraudReason.UNUSUAL_LOCATION`. Si coinciden → retorna vacío (ubicación habitual).

**5. Resultado**  
El resultado se integra al `Set<FraudReason>` que luego consume el servicio de dominio para clasificar riesgo.

### Tests implementados
- `LocationRuleTest`: países iguales (esperado vacío) y países diferentes (esperado `UNUSUAL_LOCATION`).

---

## HU3 – Marcar transacción sospechosa (3 SP)

### Enfoque de desarrollo

**1. Recepción de resultados de evaluación**  
El caso de uso `EvaluateTransactionUseCase` ejecuta secuencialmente `AmountRule` y `LocationRule`, recopilando las razones disparadas en un `Set<FraudReason>`.

**2. Reglas configurables**  
Las reglas implementan la interfaz `FraudRule` con método `evaluate(Transaction)`. El umbral del monto es configurable vía `application.yml` y se inyecta a través del puerto `FraudThresholdProvider`. Agregar o modificar reglas no requiere cambiar el orquestador.

**3. Determinación de sospecha**  
En `FraudDomainService.evaluate(Set<FraudReason>)`:
- Si `reasons.size() > 0` → `suspicious = true`
- Si vacío → `suspicious = false`

**4. Integración con flujo general**  
El `EvaluateTransactionUseCase` orquesta el flujo completo:  
guardar transacción → evaluar reglas → calcular resultado → auditar → retornar.

**5. Retorno del resultado**  
Se retorna un `FraudEvaluationResult` (record) con: `suspicious`, `riskLevel` y `reasons`. El controller lo traduce a `FraudEvaluationResponse` vía endpoint `POST /api/v1/fraud/evaluate`.

### Tests implementados
- `EvaluateTransactionUseCaseTest`: transacción con monto alto → sospechosa; transacción normal → no sospechosa. Verificación de que los puertos se invocan correctamente.

---

## HU4 – Clasificar nivel de riesgo (5 SP)

### Enfoque de desarrollo

**1. Definición de niveles de riesgo**  
Se creó el enum `RiskLevel` con tres valores: `LOW`, `MEDIUM`, `HIGH`.

**2. Servicio de clasificación**  
`FraudDomainService` combina los resultados de monto y ubicación aplicando la siguiente lógica basada en la cantidad de reglas disparadas:

| Reglas disparadas | Nivel de riesgo |
|-------------------|-----------------|
| 0                 | `LOW`           |
| 1                 | `MEDIUM`        |
| 2+                | `HIGH`          |

**3. Reglas configurables**  
La clasificación se basa en cuántas `FraudReason` se acumulan. Si se añaden nuevas reglas al conjunto, la clasificación escala automáticamente.

**4. Asignación de riesgo**  
Se implementó con un `switch` sobre `reasons.size()`:
- `0` → `LOW` (ninguna condición cumplida)
- `1` → `MEDIUM` (solo monto alto **o** solo ubicación inusual)
- `2+` → `HIGH` (monto alto **y** ubicación inusual)

**5. Retorno del resultado**  
El `FraudEvaluationResult` incluye el `riskLevel` asignado. Se persiste en `fraud_evaluations` como String y se expone vía la API REST, incluyendo el endpoint de historial `GET /api/v1/fraud/evaluations`.

### Tests implementados
- Validación end-to-end a través del use case: monto alto con mismo país → `MEDIUM`; monto bajo con mismo país → `LOW`.

---

## Arquitectura aplicada

Se usó **Arquitectura Hexagonal** con separación clara en tres capas:

- **Dominio**: `Transaction`, `FraudEvaluationResult`, `RiskLevel`, `FraudReason`, `AmountRule`, `LocationRule`, `FraudDomainService` — sin dependencias externas.
- **Aplicación**: `EvaluateTransactionUseCase`, `GetFraudEvaluationHistoryUseCase`, puertos de salida (`SaveTransactionPort`, `FraudEvaluationAuditPort`, etc.).
- **Infraestructura**: Controller REST, entidades JPA, adaptadores de persistencia, configuración (threshold, CORS, manejo global de errores).

**Stack**: Java + Spring Boot, PostgreSQL, JPA/Hibernate, Jakarta Validation.  
**Metodología**: TDD con commits atómicos por paso (RED → GREEN → REFACTOR).
