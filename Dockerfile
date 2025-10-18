FROM gradle:8.0.2-jdk17 AS builder
WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradle gradle/
COPY src src/

USER root
RUN chown -R gradle:gradle /app
USER gradle
RUN gradle clean --refresh-dependencies
RUN gradle build -x test --no-daemon
RUN gradle clean build -x test --no-daemon

FROM openjdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]