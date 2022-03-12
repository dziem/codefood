FROM openjdk:11
MAINTAINER dziem
ENV MYSQL_HOST=host.docker.internal
ENV MYSQL_PORT=3306
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=1234
ENV MYSQL_DBNAME=codefood
ADD target/codefood-0.0.1-SNAPSHOT.jar codefood-0.0.1-SNAPSHOT.jar
EXPOSE 3030
ENTRYPOINT ["java", "-jar", "codefood-0.0.1-SNAPSHOT.jar"]