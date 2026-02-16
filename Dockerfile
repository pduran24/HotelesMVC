FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar hoteles.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hoteles.jar"]