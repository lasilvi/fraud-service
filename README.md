# fraud-service

Fraud detection microservice built with Java 17 and Spring Boot.

## Endpoints

### Health

- Method: `GET`
- URL: `/api/v1/fraud/health`

Example:

```bash
curl http://localhost:8080/api/v1/fraud/health
```

### Evaluate Transaction

- Method: `POST`
- URL: `/api/v1/fraud/evaluate`
- Body:

```json
{
	"amount": 17000,
	"transactionCountry": "US",
	"userCountry": "CO"
}
```

Example:

```bash
curl -X POST http://localhost:8080/api/v1/fraud/evaluate \
	-H "Content-Type: application/json" \
	-d '{"amount":17000,"transactionCountry":"US","userCountry":"CO"}'
```

Expected response format:

```json
{
	"suspicious": true,
	"riskLevel": "HIGH",
	"reasons": ["HIGH_AMOUNT", "UNUSUAL_LOCATION"]
}
```

### List Recent Evaluations

- Method: `GET`
- URL: `/api/v1/fraud/evaluations`
- Query param: `limit` (optional, default 20, max 100)

Example:

```bash
curl "http://localhost:8080/api/v1/fraud/evaluations?limit=10"
```

## Run The Project

### Option 1: Docker (recommended)

Build image and run container:

```bash
docker build -t fraud-service:local .
docker run --rm -p 8080:8080 fraud-service:local
```

If you run it this way, set PostgreSQL connection variables:

```bash
docker run --rm -p 8080:8080 \
	-e DB_URL=jdbc:postgresql://host.docker.internal:5432/frauddb \
	-e DB_USERNAME=fraud \
	-e DB_PASSWORD=fraud \
	fraud-service:local
```

### Option 2: Docker Compose

```bash
docker compose up --build
```

`docker compose` starts both services: fraud API and PostgreSQL.

### Stop Container

If running detached with a custom container name:

```bash
docker rm -f fraud-service-local
```