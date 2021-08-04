FROM maven:3.6.0-jdk-11-slim AS build

RUN mkdir -p /app/api

FROM openjdk:11-jre-slim

COPY --from=build /app/target/tasks-backend.jar /app/api/tasks-backend.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/api/tasks-backend.jar"]