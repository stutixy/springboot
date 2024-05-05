FROM openjdk:17-jdk-alpine
MAINTAINER authors="Stuti"
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]