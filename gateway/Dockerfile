FROM openjdk:17-jdk-slim

COPY ./gateway/build/libs/gateway-0.0.1-SNAPSHOT.jar gateway-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/gateway-1.0.0.jar"]