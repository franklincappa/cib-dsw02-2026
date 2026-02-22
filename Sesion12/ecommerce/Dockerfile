# ==============================
# STAGE 1: Build
# ==============================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia primero el pom.xml para aprovechar cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests

# ==============================
# STAGE 2: Runtime
# ==============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuario no root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copia solo el JAR generado del stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Cambia al usuario no root
USER appuser

# Puerto expuesto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
