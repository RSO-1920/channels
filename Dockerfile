FROM openjdk:11.0.4-jre-slim

RUN mkdir /app

WORKDIR /app

ADD channels-api/target/channels-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "channels-api-1.0.0-SNAPSHOT.jar"]