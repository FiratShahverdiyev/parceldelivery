FROM openjdk:17-jdk-slim

COPY ./ms-courier/build/libs/ms-courier-0.0.1-SNAPSHOT.jar ms-courier-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/ms-courier-1.0.0.jar"]