FROM bellsoft/liberica-openjdk-alpine-musl:17.0.10
VOLUME /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]