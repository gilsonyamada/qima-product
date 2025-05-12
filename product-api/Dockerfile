# Use an official OpenJDK runtime based on Alpine as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Install necessary tools and clean cache
RUN apk add --no-cache curl unzip

# Copy the application's JAR file to the container
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]