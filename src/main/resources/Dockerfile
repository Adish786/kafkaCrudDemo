# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .

# Download all required dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Package the application (skip tests for faster builds; change if needed)
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port (default 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "kafkaProject.jar"]