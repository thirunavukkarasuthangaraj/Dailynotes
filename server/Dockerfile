# Stage 1: Build the app using Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the packaged JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/daliyworklog 0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
