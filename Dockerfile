# Build Image
# Builds the java code and packages it using "mvn".
# The output jar will be saved into: /build/target/kubernetes-api-0.0.1-SNAPSHOT.jar
FROM maven:3.5.2-jdk-8-alpine AS build-image
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

# Final Image
# Gets the package from the previous stage and copies to "/app/app.jar".
# Downloads and installs "kubectl" to allow the communication with the Kubernetes API Server.
# Sets the spring profile to "prod", so the code can use the "prod" application properties.
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build-image /build/target/kubernetes-api-0.0.1-SNAPSHOT.jar /app/app.jar
RUN wget https://storage.googleapis.com/kubernetes-release/release/v1.17.3/bin/linux/amd64/kubectl \
    && chmod +x ./kubectl \
    && mv ./kubectl /usr/local/bin/kubectl
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]