FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew bootJar -x test

EXPOSE 8000

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/

CMD ["java", "-jar", "build/libs/gateway-0.0.1-SNAPSHOT.jar"]
