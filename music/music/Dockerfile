# Estágio de build com Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml .
COPY src ./src

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline

# Build do projeto
RUN mvn clean package -DskipTests

# Estágio de runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Instalar dependências do sistema
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copiar o arquivo WAR gerado
COPY --from=build /app/target/*.war app.war

# Expor porta da aplicação
EXPOSE 7070

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:7070/actuator/health || exit 1

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.war", "--server.port=7070"]