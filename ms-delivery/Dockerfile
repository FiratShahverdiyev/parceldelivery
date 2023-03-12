FROM openjdk:17-jdk-slim

COPY ./ms-delivery/build/libs/ms-delivery-0.0.1-SNAPSHOT.jar ms-delivery-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/ms-delivery-1.0.0.jar"]