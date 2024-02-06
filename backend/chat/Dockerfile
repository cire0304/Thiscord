FROM eclipse-temurin:17 AS BUILD
WORKDIR /app
COPY . ./
RUN ./gradlew build -x test

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=BUILD /app/build/libs/chat-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
