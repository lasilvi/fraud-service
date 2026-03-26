# Fraud Detection Service

Sistema completo de detección de fraude con backend Spring Boot y frontend React.

## 📋 Descripción

Sistema que evalúa transacciones en tiempo real para detectar actividad sospechosa basado en reglas de negocio como monto y ubicación geográfica. Proporciona una interfaz web moderna para evaluar transacciones y consultar el historial de evaluaciones.

## 🏗️ Arquitectura

El proyecto está dividido en dos microservicios:

- **fraud-backend**: API REST construida con Spring Boot + PostgreSQL
- **fraud-frontend**: Aplicación React + TypeScript + TailwindCSS

## 🚀 Inicio Rápido con Docker

La forma más sencilla de ejecutar todo el sistema es usando Docker Compose:

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# O en modo detached (background)
docker-compose up -d --build
```

Los servicios estarán disponibles en:

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **PostgreSQL**: localhost:5432

Para detener los servicios:

```bash
docker-compose down

# Para eliminar también los volúmenes (base de datos)
docker-compose down -v
```

## 📦 Tecnologías

### Backend

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- PostgreSQL 16
- Maven
- JUnit 5 + Mockito

### Frontend

- React 19
- TypeScript
- Vite
- TailwindCSS
- React Router
- Axios

### DevOps

- Docker & Docker Compose
- Nginx (para servir el frontend en producción)

## 📚 API Endpoints

### Health Check

```bash
GET http://localhost:8080/api/v1/fraud/health
```

### Evaluar Transacción

```bash
POST http://localhost:8080/api/v1/fraud/evaluate
Content-Type: application/json

{
  "amount": 17000,
  "transactionCountry": "US",
  "userCountry": "CO"
}
```

Respuesta:

```json
{
  "suspicious": true,
  "riskLevel": "HIGH",
  "reasons": [
    "Transaction amount exceeds threshold",
    "Transaction country different from user country"
  ]
}
```

### Obtener Historial

```bash
GET http://localhost:8080/api/v1/fraud/evaluations?limit=10
```

## 🧪 Ejecutar Tests

### Backend

```bash
cd fraud-backend
mvn test

# Con cobertura
mvn test jacoco:report
```

### Frontend

```bash
cd fraud-frontend
npm test

# Con cobertura
npm run test:coverage
```

## 🛠️ Desarrollo Local

### Backend

Requisitos:

- Java 17+
- Maven 3.6+
- PostgreSQL 16 (o usar Docker)

```bash
cd fraud-backend

# Levantar solo PostgreSQL con Docker
docker run -d \
  --name fraud-postgres \
  -e POSTGRES_DB=frauddb \
  -e POSTGRES_USER=fraud \
  -e POSTGRES_PASSWORD=fraud \
  -p 5432:5432 \
  postgres:16-alpine

# Ejecutar aplicación
mvn spring-boot:run
```

### Frontend

Requisitos:

- Node.js 20+
- npm

```bash
cd fraud-frontend

# Instalar dependencias
npm install

# Copiar variables de entorno
cp .env.example .env

# Ejecutar en modo desarrollo
npm run dev
```

El frontend estará disponible en http://localhost:3000 con hot-reload.

## 📂 Estructura del Proyecto

```
fraud-service/
├── fraud-backend/          # Servicio backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fraud/
│   │   │   │   ├── application/    # Casos de uso
│   │   │   │   ├── domain/         # Lógica de negocio
│   │   │   │   └── infrastructure/ # Controladores, config, persistencia
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── Dockerfile
│   └── pom.xml
│
├── fraud-frontend/         # Aplicación web
│   ├── src/
│   │   ├── components/     # Componentes reutilizables
│   │   ├── pages/          # Páginas de la aplicación
│   │   ├── services/       # Servicios de API
│   │   ├── App.tsx         # Componente raíz con routing
│   │   └── main.tsx        # Entry point
│   ├── Dockerfile
│   ├── package.json
│   └── vite.config.ts
│
└── docker-compose.yml      # Orquestación de servicios
```

## 🔧 Configuración

### Backend

Variables de entorno (application.yml):

- `DB_URL`: URL de conexión a PostgreSQL (default: jdbc:postgresql://localhost:5432/frauddb)
- `DB_USERNAME`: Usuario de la base de datos (default: fraud)
- `DB_PASSWORD`: Contraseña de la base de datos (default: fraud)

### Frontend

Variables de entorno (.env):

- `VITE_API_BASE_URL`: URL del backend API (default: http://localhost:8080)

## 🎯 Reglas de Fraude

El sistema actualmente evalúa dos reglas:

1. **AmountRule**: Transacciones > $15,000 USD se marcan como sospechosas
2. **LocationRule**: Transacciones desde un país diferente al del usuario se marcan como sospechosas

Niveles de riesgo:

- **LOW**: Sin razones de sospecha
- **MEDIUM**: 1 regla activada
- **HIGH**: 2+ reglas activadas

## 📄 Licencia

Este proyecto es código privado para fines educativos.

## 👥 Autores

- Desarrollado como proyecto académico para Sofka U

---

Para más detalles sobre cada servicio, consulta los README específicos:

- [Backend README](fraud-backend/README.md)
- [Frontend README](fraud-frontend/README.md)
