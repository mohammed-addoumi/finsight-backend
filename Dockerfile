# Step 1: Use an Alpine-based JDK as the base image
FROM openjdk:17-jdk-alpine

# Step 3: Set the working directory
WORKDIR /app

# Step 4: Copy the application JAR file into the container
# Replace 'app.jar' with the name of your Spring Boot JAR file
COPY target/finsight-backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Step 5: Expose the port the app runs on
# Replace '8080' with your application's configured port if different
EXPOSE 8080

# Step 6: Define the entry point for the container
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]

