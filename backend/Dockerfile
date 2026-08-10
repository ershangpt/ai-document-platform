FROM maven:3.9.16-eclipse-temurin-21 AS build

WORKDIR /app

# Build shared contract library
COPY common-events/pom.xml common-events/pom.xml
COPY common-events/src common-events/src

RUN mvn -f common-events/pom.xml clean install -DskipTests

# Build AI service
COPY ai-service/pom.xml ai-service/pom.xml
COPY ai-service/src ai-service/src

RUN mvn -f ai-service/pom.xml clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/ai-service/target/*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]