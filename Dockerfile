FROM maven:3.8.3-openjdk-17

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Xmx2g","-jar","/app/app.jar"]