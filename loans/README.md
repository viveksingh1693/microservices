# Loans microservice

Small Spring Boot service that manages loans data.

## Build
Use the included Maven wrapper:

```powershell
cd loans
.\mvnw.cmd clean package
```

## Run

```powershell
java -jar target\loans-0.0.1-SNAPSHOT.jar
```

Default port is configured in `src/main/resources/application.properties`.

## Docker
Build and run with Docker:

```powershell
docker build -t microservices_loans:latest .
docker run -p 8082:8082 microservices_loans:latest
```

## Endpoints
- The controller class is `com.viv.loans.controller.LoansController` — inspect it for routes and DTO shapes.

## Notes
- Schema file: `src/main/resources/schema.sql`.
