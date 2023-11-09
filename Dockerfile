FROM maven:3.8.3-openjdk-17

COPY /target/weightminder-0.0.1-SNAPSHOT.jar /data/weightminder-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java", "-XX:+UseParallelGC", "-Xmx2g", "-jar", "/data/weightminder-0.0.1-SNAPSHOT.jar"]