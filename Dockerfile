# Використовуємо базовий образ з JDK та Maven
FROM maven:3.8.4-openjdk-17 AS builder

# Копіюємо pom.xml окремо, щоб спростити кешування
COPY pom.xml /tmp/
WORKDIR /tmp
RUN mvn dependency:go-offline

# Копіюємо всі файли проекту і збираємо його
COPY . /tmp/
RUN mvn -B package --file /tmp/pom.xml

FROM openjdk:17

# Копіюємо скомпільований JAR файл з попереднього етапу в новий образ
COPY --from=builder /tmp/target/*.jar /app/application.jar

# Виконуємо ваше Java-додаток
CMD ["java", "-jar", "/app/application.jar"]


# docker build -t data-service .
# docker images or docker ps
# docker run -p 8081:8080 data-service
# docker run -d --publish 8088:8080 test

# docker stop
# docker pull postgres

# if exist compose.yml
# docker-compose build
# docker-compose up