FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY math-core-platform/pom.xml math-core-platform/pom.xml
COPY math-ai-gateway/pom.xml math-ai-gateway/pom.xml
COPY math-legacy-portal/pom.xml math-legacy-portal/pom.xml
COPY math-core-platform/src math-core-platform/src
RUN mvn -pl math-core-platform -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/math-core-platform/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]