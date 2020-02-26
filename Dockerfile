# Builds the java code and packages using mvn
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

# Gets the package from previous temporal image and installs kubectl
# Sets the profile to "prod", so the code can use the "prod" application properties.
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/kubernetes-api-0.0.1-SNAPSHOT.jar /app/app.jar
RUN wget https://storage.googleapis.com/kubernetes-release/release/v1.17.3/bin/linux/amd64/kubectl \
    && chmod +x ./kubectl \
    && mv ./kubectl /usr/local/bin/kubectl
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]