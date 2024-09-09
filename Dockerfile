# Use the Java 17 runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file into the container
COPY target/bookstore-0.0.1-SNAPSHOT.jar /app/bookmanagementsystem.jar

# Set the command to run the JAR file
CMD ["java", "-jar", "bookmanagementsystem.jar"]