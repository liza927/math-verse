FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY math-core-platform/pom.xml math-core-platform/pom.xml
COPY math-ai-gateway/pom.xml math-ai-gateway/pom.xml
COPY math-legacy-portal/pom.xml math-legacy-portal/pom.xml
COPY math-ai-gateway/src math-ai-gateway/src
RUN mvn -pl math-ai-gateway -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY math-ai-gateway/target/math-ai-gateway-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]