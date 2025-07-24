# Use OpenJDK base image
FROM eclipse-temurin:17-jdk-jammy

# Create app directory
WORKDIR /app

# Copy jar file (rename it if needed)
COPY target/ENotes-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["java","-jar","app.jar"]
