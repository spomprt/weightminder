#образ maven для сборки проекта
FROM maven:3.9.4 as build
#собираем проект в рабочую директорию
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package

#образ jdk11 для запуска собранного jar файла
FROM bellsoft/liberica-openjdk-alpine-musl:17.0.6-10
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]