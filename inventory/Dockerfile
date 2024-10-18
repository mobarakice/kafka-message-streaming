FROM gradle:jdk-21-and-23-jammy AS builder

WORKDIR /app
COPY . /app

#Do executable the gradlew and build project
#RUN chmod +x gradlew
#RUN ./gradlew build
RUN gradle build


FROM openjdk:21-slim
WORKDIR /app
#COPY build/libs/inventory-0.0.1-SNAPSHOT.jar inventory.jar
COPY --from=builder ./app/build/libs/inventory-0.0.1-SNAPSHOT.jar inventory.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "inventory.jar"]