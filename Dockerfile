FROM eclipse-temurin:21.0.3_9-jdk AS build
COPY . /app
WORKDIR /app


RUN ./gradlew assemble

FROM eclipse-temurin:21.0.3_9-jre AS run

ENV TZ=Asia/Seoul

## spring 패키지 복사
COPY --from=build \
  /app/build/libs/*.jar \
  /app/app.jar

EXPOSE 8080

ENTRYPOINT java \
-jar /app/app.jar
