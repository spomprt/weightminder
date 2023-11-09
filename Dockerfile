FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app

ENTRYPOINT ["java","-jar","/app/weightminder-0.0.1-SNAPSHOT.jar"]