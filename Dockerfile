FROM openjdk:8-jdk-alpine

ARG APP_NAME

COPY $APP_NAME app.jar

ENTRYPOINT java -jar app.jar
