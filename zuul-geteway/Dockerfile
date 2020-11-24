FROM adoptopenjdk/openjdk8:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=build/libs/zuul-geteway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]