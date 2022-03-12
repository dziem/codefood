FROM openjdk:11
MAINTAINER dziem
ADD target/codefood-0.0.1-SNAPSHOT.jar codefood-0.0.1-SNAPSHOT.jar
EXPOSE 3030
ENTRYPOINT ["java", "-jar", "codefood-0.0.1-SNAPSHOT.jar"]