FROM gradle:jdk-21-and-23-jammy AS builder

WORKDIR /app
COPY . /app

#Do executable the gradlew and build project
RUN chmod +x gradlew
RUN ./gradlew build
#RUN gradle build


FROM openjdk:21-slim
WORKDIR /app

COPY build/libs/order-0.0.1-SNAPSHOT.jar order.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "order.jar"]