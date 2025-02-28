FROM gradle:8.12.1-jdk17 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENV JAVA_OPTS="-Xmx4096m -Xms4096m"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]