FROM amazoncorretto:22
LABEL maintainer = "henry OLUWATOBI"
EXPOSE 8080
WORKDIR /app
COPY target/jDocker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar" ]