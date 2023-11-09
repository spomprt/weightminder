FROM maven:3.8.3-openjdk-17 as build

FROM maven:3.8.3-openjdk-17 AS final
COPY --from=build /target/weightminder-0.0.1-SNAPSHOT.jar /weightminder-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java", "-XX:+UseParallelGC", "-Xmx2g", "-jar", "/weightminder-0.0.1-SNAPSHOT.jar"]