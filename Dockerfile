FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app
COPY . /app

FROM maven:3.8.3-openjdk-17 AS final
WORKDIR /app

EXPOSE 8080
CMD ["java", "-XX:+UseParallelGC", "-Xmx2g", "-jar", "/app/weightminder-0.0.1-SNAPSHOT.jar"]