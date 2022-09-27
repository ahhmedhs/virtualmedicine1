FROM openjdk:11-jre-slim
ADD ./target/appointment-service-0.0.1-SNAPSHOT.jar /usr/src/appointment-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "appointment-service-0.0.1-SNAPSHOT.jar"]
