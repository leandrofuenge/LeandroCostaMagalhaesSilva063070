# ==========================
# Estágio de build com Maven
# ==========================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar apenas pom.xml e baixar dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build do projeto (skip testes)
RUN mvn clean package -DskipTests -B -Dmaven.compiler.encoding=UTF-8

# ==========================
# Estágio de runtime
# ==========================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Instalar curl
RUN apt-get update && apt-get install -y curl \
    && rm -rf /var/lib/apt/lists/*

# Copiar WAR do estágio de build
COPY --from=build /app/target/*.war app.war

# Porta da aplicação
EXPOSE 7070

# Health check Spring Boot actuator
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:7070/actuator/health || exit 1

# Iniciar aplicação
ENTRYPOINT ["java", "-jar", "app.war", "--server.port=7070"]
