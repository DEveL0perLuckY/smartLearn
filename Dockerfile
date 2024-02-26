FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar smartlearn.jar
ENTRYPOINT ["java","-jar","/smartlearn.jar"]
EXPOSE 8080