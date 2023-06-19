# Spring Boot

### Requirement: Java 11, [Postgre SQL](https://www.postgresql.org/)

## Installation Step:
- Download [Postgre SQL](https://www.postgresql.org/download/) and Install.
- Create Database "ist"
- Clone the project
- Compiles
```
./mvnw clean install
```
- Run executable Java program 
```
java -jar target/agil_syofian_hidayat-0.0.1-SNAPSHOT.jar
```

## Or Install with [Docker](https://www.docker.com/)
- Clone the project
- Run containers
```
docker compose up -d
```
- Confirm it all went up correctly
```
docker compose logs
```

# API Documentation and Testing

## [Swagger](https://swagger.io/)

Check documentation and testing API
```
http://localhost:8080/swagger-ui/index.html
```


## [Postman](https://www.postman.com)

Check api documentation from [here](https://documenter.getpostman.com/view/21416970/2s93si1Vgb) and
testing with postman.

Steps:

- Download and install Postman
- Import postman_collection.json

