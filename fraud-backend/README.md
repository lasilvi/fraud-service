# Fraud Detection Service

Servicio para detectar transacciones sospechosas en tiempo real.
Evalua reglas de fraude por monto y ubicación, clasifica nivel de riesgo y persiste cada evaluación para consulta posterior.

## 2. Propósito

Este servicio existe para identificar de forma temprana operaciones potencialmente fraudulentas.
Resuelve el problema de tomar decisiones de riesgo en tiempo real y dejar trazabilidad histórica de las evaluaciones.

## 3. Tecnologías

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5

## 4. Instalación

### Opción A: Local

1. Clonar repositorio:

```bash
git clone <repo-url>
```

2. Entrar al proyecto:

```bash
cd fraud-service
```

3. Levantar PostgreSQL (si no tienes uno activo):

```bash
docker compose up -d postgres
```

4. Compilar e instalar dependencias:

```bash
mvn clean install
```

### Opción B: Docker Compose (API + PostgreSQL)

```bash
docker compose up --build
```

## 5. Uso

### Ejecutar aplicación

```bash
mvn spring-boot:run
```

También puedes ejecutar con Docker Compose para levantar API y base de datos juntas.

### Endpoints

#### Health

- Method: GET
- URL: /api/v1/fraud/health

```bash
curl http://localhost:8080/api/v1/fraud/health
```

#### Evaluate Transaction

- Method: POST
- URL: /api/v1/fraud/evaluate

Request:

```json
{
  "amount": 17000,
  "transactionCountry": "US",
  "userCountry": "CO"
}
```

```bash
curl -X POST http://localhost:8080/api/v1/fraud/evaluate \
  -H "Content-Type: application/json" \
  -d '{"amount":17000,"transactionCountry":"US","userCountry":"CO"}'
```

Response ejemplo:

```json
{
  "suspicious": true,
  "riskLevel": "HIGH",
  "reasons": ["HIGH_AMOUNT", "UNUSUAL_LOCATION"]
}
```

#### List Recent Evaluations

- Method: GET
- URL: /api/v1/fraud/evaluations
- Query param: limit (opcional, default 20, max 100)

```bash
curl "http://localhost:8080/api/v1/fraud/evaluations?limit=10"
```

## 6. Estructura del proyecto

```text
src/
├── main/
│   ├── java/com/fraud/
│   │   ├── domain/           # Reglas y modelos de negocio
│   │   ├── application/      # Casos de uso y puertos
│   │   └── infrastructure/   # Controllers, config y persistencia
│   └── resources/
└── test/                     # Pruebas unitarias
```

## 7. Tests

Ejecutar pruebas unitarias:

```bash
mvn test
```