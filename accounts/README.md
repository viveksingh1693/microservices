# Accounts microservice

Small Spring Boot service that manages account/customer data.

## Build
Use the included Maven wrapper:

```powershell
cd accounts
.\mvnw.cmd clean package
```

## Run

```powershell
java -jar target\accounts-0.0.1-SNAPSHOT.jar
```

Default port is configured in `src/main/resources/application.properties` (commonly 8080).

## Docker
Build and run with Docker:

```powershell
docker build -t microservices_accounts:latest .
docker run -p 8080:8080 microservices_accounts:latest
```

## Endpoints
- The controller class is `com.viv.accounts.controller.AccountsController` — inspect it for available routes and DTO shapes.

## Notes
- Schema file: `src/main/resources/schema.sql`.
- Use the tests in `src/test/java` as a reference for basic behavior.
