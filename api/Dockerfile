# Use an official Java runtime as a base image
FROM openjdk:23-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
# Replace 'your-application.jar' with the actual name of your JAR file
COPY target/enterprise-0.0.1-SNAPSHOT.jar app.jar
# Expose the port that your Spring Boot application listens on (default: 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
