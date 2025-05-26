FROM openjdk:17-jdk-alpine

LABEL maintainer="P-Jitendra"

WORKDIR /usr/src/app

COPY target/bms_backend-0.0.1-SNAPSHOT.jar bms_backend-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "bms_backend-0.0.1-SNAPSHOT.jar"]
