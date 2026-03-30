# ---------- BUILD ----------
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /workspace

# 1. Copiar solo dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# 2. Copiar código
COPY src ./src

# 3. Compilar SIN tests (más rápido)
RUN mvn -B clean package -DskipTests

# ---------- RUNTIME ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
