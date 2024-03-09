FROM bellsoft/liberica-openjdk-alpine-musl:17.0.6-10 AS build

WORKDIR /app
RUN apk add --no-cache openjdk17 maven
COPY . .

RUN mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-alpine-musl:17.0.6-10

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Xmx64m","-jar","app.jar"]
