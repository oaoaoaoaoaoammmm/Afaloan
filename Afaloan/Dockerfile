FROM gradle:8.8 as build

WORKDIR /workspace

COPY config ./config
COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts

RUN gradle clean build -x test

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /workspace/build/libs/afaloan-0.1.jar ./afaloan.jar

ENTRYPOINT ["java", "-jar", "afaloan.jar"]