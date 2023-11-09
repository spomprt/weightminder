FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app

COPY app/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]