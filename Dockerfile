FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/*.jar hoteles.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hoteles.jar"]