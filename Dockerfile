# ---------------------------------------------------------------------------
# SIGA People MicroService - Dockerfile
# Java 21, Spring Boot 3.5. Multi-stage build.
# ---------------------------------------------------------------------------

# Etapa 1: Build con Maven
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

RUN chmod +x mvnw

# Settings que permite HTTP (Nexus) con Maven 3.8.1+
ARG MVN_SETTINGS=".mvn/settings-docker.xml"
RUN ./mvnw dependency:go-offline -B -s "${MVN_SETTINGS}" || true

COPY src src

RUN ./mvnw package -DskipTests -B -s "${MVN_SETTINGS}"

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -g 1000 appgroup && adduser -u 1000 -G appgroup -D appuser
USER appuser

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 9406

ENTRYPOINT ["java", "-jar", "app.jar"]
