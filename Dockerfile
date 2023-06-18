FROM openjdk:11-slim-buster as build

COPY .mvn .mvn                                               
COPY mvnw .                                                  
COPY pom.xml .                                               
COPY src src
ADD docker.properties src/main/resources/application.properties

RUN ./mvnw -B package -DskipTests                                   

FROM openjdk:11-jre-slim-buster                              

COPY --from=build target/agil_syofian_hidayat-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "agil_syofian_hidayat-0.0.1-SNAPSHOT.jar"]