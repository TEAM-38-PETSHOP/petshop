# Builder stage
FROM openjdk:21-jdk as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract


# Final stage
FROM openjdk:21-jdk-slim
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080


# docker build -t data-service .
# docker images or docker ps
# docker run -p 8081:8080 data-service
# docker run -d --publish 8088:8080 test

# docker stop
# docker pull postgres

# if exist compose.yml
# docker-compose build
# docker-compose up