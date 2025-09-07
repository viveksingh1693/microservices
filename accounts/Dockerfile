# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

# Add Maintainer Info
LABEL maintainer="Vivek"

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]

# Make port 8080 available to the world outside this container
EXPOSE 8080