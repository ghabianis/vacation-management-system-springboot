# First stage: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

# Second stage: Use Maven to run the application
FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
# Copy the Maven settings and dependencies from the first stage
COPY --from=build /root/.m2 /root/.m2
# Copy the source code and pom.xml from the first stage
COPY --from=build /app/src /app/src
COPY --from=build /app/pom.xml /app
EXPOSE 8080
ENTRYPOINT ["mvn", "-X","spring-boot:run"]
