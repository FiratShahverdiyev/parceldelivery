FROM openjdk:17-jdk-slim

COPY ./ms-auth-admin/build/libs/ms-auth-admin-0.0.1-SNAPSHOT.jar ms-auth-admin-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/ms-auth-admin-1.0.0.jar"]