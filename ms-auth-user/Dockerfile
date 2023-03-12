FROM openjdk:17-jdk-slim

COPY ./ms-auth-user/build/libs/ms-auth-user-0.0.1-SNAPSHOT.jar ms-auth-user-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/ms-auth-user-1.0.0.jar"]