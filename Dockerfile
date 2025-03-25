FROM eclipse-temurin:21
EXPOSE 8080
COPY backend/target/team-project-2.jar /team-project-2.jar

ENTRYPOINT ["java", "-jar", "/super-nutrition-tracker.jar"]