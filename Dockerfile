# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven executable and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/shop.jar"]