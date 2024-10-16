# Stage 1: Build the application
FROM maven:3.8.6 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and any necessary files
COPY pom.xml ./
COPY src ./src

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/library-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

