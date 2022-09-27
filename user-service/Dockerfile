FROM openjdk:11-jre-slim
ADD ./target/user-service-0.0.1-SNAPSHOT.jar /usr/src/user-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "user-service-0.0.1-SNAPSHOT.jar"]
