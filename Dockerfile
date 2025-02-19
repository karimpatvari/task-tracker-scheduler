# Use an official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the built JAR file from Gradle output
COPY build/libs/*.jar app.jar

# Expose the port that the application listens on (e.g., 8080 for internal container communication)
EXPOSE 8082

# Run the Spring Boot application
CMD ["sh", "-c", "echo 'Waiting for Kafka...' && sleep 10 && java -jar app.jar"]
