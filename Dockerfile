FROM eclipse-temurin:21-jdk-alpine
LABEL maintainer = "henry OLUWATOBI"
EXPOSE 8080
WORKDIR /app
COPY target/bethen-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar","app.jar" ]