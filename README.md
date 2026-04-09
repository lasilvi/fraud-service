# Fraud Detection Service

Sistema de detección de fraude basado en microservicios con Spring Boot, RabbitMQ y frontend React.

## Descripción

Plataforma que evalúa transacciones en tiempo real para detectar actividad sospechosa basándose en reglas de negocio configurables (monto y ubicación geográfica). Incluye gestión centralizada de configuración, alertas asíncronas vía mensajería y una interfaz web moderna para operar el sistema completo.

## Arquitectura

El proyecto sigue una **arquitectura de microservicios** con comunicación síncrona (HTTP) y asíncrona (RabbitMQ):

```
┌──────────────────────────────────────────────────────────────┐
│                     FRONTEND (React)                         │
│  Evaluar Transacciones · Alertas · Configuración · Historial│
└────────────┬─────────────────────────────────────────────────┘
             │ HTTP
             ├──────────────────────────────┐
             ▼                              ▼
  ┌─────────────────────┐       ┌─────────────────────┐
  │ TRANSACTION-SERVICE │       │  CONFIG-SERVICE      │
  │   (Puerto 8080)     │──────▶│   (Puerto 8081)      │
  │                     │ HTTP  │                      │
  │ Evalúa transacciones│       │ Umbrales, reglas,    │
  │ Historial           │       │ ubicaciones de usuario│
  └─────────┬───────────┘       └──────────────────────┘
            │
            │ Publica alertas (RabbitMQ)
            ▼
  ┌──────────────────────────────┐
  │    RabbitMQ Message Broker   │
  │  Exchange: fraud.exchange    │
  │  Queue: fraud.alert.queue    │
  └──────────┬───────────────────┘
             │ Consume mensajes
             ▼
  ┌─────────────────────┐
  │   ALERT-SERVICE     │
  │   (Puerto 8082)     │
  │                     │
  │ Persiste y expone   │
  │ alertas de fraude   │
  └─────────────────────┘
             │
             ▼
  ┌──────────────────────┐
  │   PostgreSQL (5432)  │
  │   Base de datos      │
  │   compartida         │
  └──────────────────────┘
```

### Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **transaction-service** | 8080 | Evaluación de fraude en tiempo real. Aplica reglas de negocio, persiste resultados y publica alertas a RabbitMQ |
| **config-service** | 8081 | Gestión centralizada de umbrales, reglas de riesgo y perfiles de ubicación de usuarios |
| **alert-service** | 8082 | Consume eventos de fraude desde RabbitMQ, persiste alertas y las expone vía REST |
| **frontend** | 3000 | Interfaz web para evaluar transacciones, consultar alertas, ver historial y administrar configuración |
| **PostgreSQL** | 5432 | Base de datos relacional compartida por los tres microservicios |
| **RabbitMQ** | 5672 / 15672 | Broker de mensajería para comunicación asíncrona entre transaction-service y alert-service |

## Funcionalidades

### Evaluación de transacciones
- Evaluación en tiempo real con respuesta inmediata de nivel de riesgo
- Reglas de negocio extensibles mediante interfaz `FraudRule`
- Persistencia de cada evaluación para auditoría

### Reglas de fraude configurables
- **AmountRule**: Transacciones que superan el umbral configurado (default: $15,000) se marcan como sospechosas
- **LocationRule**: Transacciones desde un país diferente al registrado del usuario se marcan como sospechosas
- Umbral configurable en caliente desde el config-service

### Clasificación de riesgo

| Reglas activadas | Nivel de riesgo |
|------------------|-----------------|
| 0                | `LOW`           |
| 1                | `MEDIUM`        |
| 2+               | `HIGH`          |

### Alertas asíncronas
- transaction-service publica eventos de fraude a RabbitMQ (`fraud.exchange` → `fraud.alert`)
- alert-service consume los eventos y persiste las alertas automáticamente
- Panel de alertas en el frontend para monitoreo en tiempo real

### Gestión de configuración
- Actualizar umbral de monto desde la UI o API REST
- Administrar perfiles de ubicación por usuario
- Crear y eliminar reglas de riesgo personalizadas

### Interfaz web
- **Evaluar transacción**: formulario con User ID, monto y país de transacción
- **Alertas**: tabla con todas las alertas generadas por el sistema
- **Historial**: evaluaciones recientes con detalles de riesgo
- **Configuración**: panel de administración de umbrales, ubicaciones y reglas

## Despliegue con Docker

### Requisitos previos

- [Docker](https://docs.docker.com/get-docker/) 20+
- [Docker Compose](https://docs.docker.com/compose/install/) v2+

### Inicio rápido

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# O en modo detached (background)
docker-compose up -d --build
```

Servicios disponibles tras el despliegue:

| Servicio | URL |
|----------|-----|
| Frontend | http://localhost:3000 |
| Transaction API | http://localhost:8080 |
| Config API | http://localhost:8081 |
| Alert API | http://localhost:8082 |
| RabbitMQ Management | http://localhost:15672 (guest/guest) |
| PostgreSQL | localhost:5432 |

### Detener servicios

```bash
# Detener
docker-compose down

# Detener y eliminar volúmenes (borra datos de BD)
docker-compose down -v
```

### Variables de entorno

Los servicios backend aceptan las siguientes variables (con valores por defecto para desarrollo):

| Variable | Default | Descripción |
|----------|---------|-------------|
| `DB_URL` | `jdbc:postgresql://postgres:5432/frauddb` | URL de conexión a PostgreSQL |
| `DB_USERNAME` | `fraud` | Usuario de base de datos |
| `DB_PASSWORD` | `fraud` | Contraseña de base de datos |
| `RABBITMQ_HOST` | `rabbitmq` | Host del broker RabbitMQ |
| `RABBITMQ_PORT` | `5672` | Puerto AMQP de RabbitMQ |
| `RABBITMQ_USERNAME` | `guest` | Usuario de RabbitMQ |
| `RABBITMQ_PASSWORD` | `guest` | Contraseña de RabbitMQ |
| `CONFIG_SERVICE_URL` | `http://config-service:8081` | URL del config-service (solo transaction-service) |

## API Endpoints

### Transaction Service (puerto 8080)

```bash
# Health check
GET /api/v1/fraud/health

# Evaluar transacción
POST /api/v1/fraud/evaluate
Content-Type: application/json

{
  "amount": 17000,
  "transactionCountry": "US",
  "userCountry": "CO"
}

# Respuesta
{
  "suspicious": true,
  "riskLevel": "HIGH",
  "reasons": [
    "Transaction amount exceeds threshold",
    "Transaction country different from user country"
  ]
}

# Historial de evaluaciones
GET /api/v1/fraud/evaluations?limit=10
```

### Config Service (puerto 8081)

```bash
# Obtener umbral actual
GET /api/v1/config/threshold

# Actualizar umbral
PUT /api/v1/config/threshold
Content-Type: application/json
{ "value": 20000 }

# Obtener ubicación de usuario
GET /api/v1/config/user-location/{userId}

# Guardar ubicación de usuario
PUT /api/v1/config/user-location
Content-Type: application/json
{ "userId": "user-1", "country": "CO" }

# Listar reglas de riesgo
GET /api/v1/config/risk-rules

# Crear regla de riesgo
POST /api/v1/config/risk-rules

# Eliminar regla de riesgo
DELETE /api/v1/config/risk-rules/{id}
```

### Alert Service (puerto 8082)

```bash
# Obtener todas las alertas
GET /api/v1/alert/alerts
```

## Desarrollo Local

### Backend

**Requisitos**: Java 17+, Maven 3.6+

```bash
# Levantar infraestructura (PostgreSQL + RabbitMQ)
docker-compose up -d postgres rabbitmq

# Ejecutar cada servicio
cd backend/transaction-service && mvn spring-boot:run
cd backend/config-service && mvn spring-boot:run
cd backend/alert-service && mvn spring-boot:run
```

### Frontend

**Requisitos**: Node.js 20+, npm

```bash
cd frontend
npm install
npm run dev
```

El frontend estará disponible en http://localhost:5173 con hot-reload.

### Ejecutar tests

```bash
# Tests de cada microservicio
cd backend/transaction-service && mvn test
cd backend/config-service && mvn test
cd backend/alert-service && mvn test
```

## Tecnologías

### Backend

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring AMQP (RabbitMQ)
- PostgreSQL 16
- Maven
- JUnit 5 + Mockito

### Frontend

- React 19
- TypeScript 5.9
- Vite
- TailwindCSS
- React Router
- Axios

### Infraestructura

- Docker & Docker Compose
- RabbitMQ 3 (con Management UI)
- Nginx (frontend en producción)
- PostgreSQL 16

## Estructura del Proyecto

```
fraud-service/
├── backend/
│   ├── transaction-service/    # Evaluación de fraude (puerto 8080)
│   │   ├── src/
│   │   │   ├── main/java/com/fraud/
│   │   │   │   ├── application/        # Casos de uso
│   │   │   │   ├── domain/             # Reglas, modelos, servicios de dominio
│   │   │   │   └── infrastructure/     # Controllers, JPA, RabbitMQ publisher
│   │   │   └── resources/application.yml
│   │   ├── Dockerfile
│   │   └── pom.xml
│   │
│   ├── config-service/         # Configuración centralizada (puerto 8081)
│   │   ├── src/main/java/com/fraud/config/
│   │   │   ├── application/
│   │   │   ├── domain/
│   │   │   └── infrastructure/
│   │   ├── Dockerfile
│   │   └── pom.xml
│   │
│   └── alert-service/          # Alertas de fraude (puerto 8082)
│       ├── src/main/java/com/fraud/alert/
│       │   ├── application/
│       │   ├── domain/
│       │   └── infrastructure/     # Controller, RabbitMQ listener
│       ├── Dockerfile
│       └── pom.xml
│
├── frontend/                   # Aplicación web React
│   ├── src/
│   │   ├── components/         # Componentes reutilizables
│   │   ├── pages/              # EvaluateTransaction, Alerts, Config, History
│   │   ├── hooks/              # Custom hooks
│   │   ├── services/           # Clientes HTTP (Axios)
│   │   └── utils/              # Constantes, formatters, theme
│   ├── Dockerfile
│   └── package.json
│
├── docker-compose.yml          # Orquestación de todos los servicios
├── DEV_TASKS.md                # Documentación de desarrollo (TDD)
└── README.md
```

## Patrón arquitectónico

Cada microservicio sigue **Arquitectura Hexagonal** con separación en tres capas:

- **Dominio**: Modelos, reglas de negocio y servicios de dominio (sin dependencias externas)
- **Aplicación**: Casos de uso y puertos de entrada/salida
- **Infraestructura**: Controllers REST, entidades JPA, adaptadores de persistencia y mensajería

## Licencia

Proyecto académico privado para Sofka U.

## Autores

Desarrollado como proyecto académico para Sofka U.
