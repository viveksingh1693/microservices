# Cards microservice

Small Spring Boot service that manages card data.

## Build
Use the included Maven wrapper:

```powershell
cd cards
.\mvnw.cmd clean package
```

## Run

```powershell
java -jar target\cards-0.0.1-SNAPSHOT.jar
```

Default port is configured in `src/main/resources/application.properties`.

## Docker
Build and run with Docker:

```powershell
docker build -t microservices_cards:latest .
docker run -p 8081:8081 microservices_cards:latest
```

## Endpoints
- The controller class is `com.viv.cards.controller.CardsController` — inspect it for routes and DTO shapes.

## Notes
- Schema file: `src/main/resources/schema.sql`.
