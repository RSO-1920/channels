FROM openjdk:11.0.4-jre-slim

RUN mkdir /app

WORKDIR /app

ADD /target/rso-channels-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "rso-channels-1.0-SNAPSHOT.jar"]