# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copia SOLO los archivos del servicio actual (evita contaminación con otros servicios)
COPY pedidoservice/pom.xml .
COPY pedidoservice/src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
# Copia el .jar específico de este servicio
COPY --from=build /app/target/pedidoservice-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]