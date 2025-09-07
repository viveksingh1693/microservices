# Microservices sample (accounts, cards, loans)

This repository contains three simple Spring Boot microservices (Accounts, Cards, Loans) organized as independent Maven modules. Each service is runnable as a standalone Spring Boot application, can be built into a fat JAR, and can be containerized with Docker. A `docker-compose.yml` at the repo root can be used to bring the services up together.

## Services
- `accounts/` — Accounts microservice
- `cards/` — Cards microservice
- `loans/` — Loans microservice

## Prerequisites
- JDK 17+ (or the version your project targets)
- Docker & Docker Compose (optional, for containers)
- Git (optional)

On Windows you can use the included Maven wrapper (`mvnw.cmd`) so you don't need a locally installed Maven.

## Quick build & run (locally)
From the repo root you can build each service using the Maven wrapper. Example for `accounts`:

```powershell
cd accounts
.\mvnw.cmd clean package
```

Then run the fat JAR produced in `target/`:

```powershell
java -jar target\accounts-0.0.1-SNAPSHOT.jar
```

Repeat for `cards` and `loans`.

## Run using Docker
Build images for each service (example for `accounts`):

```powershell
docker build -t microservices_accounts:latest ./accounts
```

Run with Docker Compose (recommended for running all services together):

```powershell
docker-compose up --build
```

The `docker-compose.yml` in the repo root controls networking and port mappings.

## Where to find configuration
- Per-service Spring Boot properties: `./<service>/src/main/resources/application.properties`.
- SQL schema (if present): `./<service>/src/main/resources/schema.sql`.

## Troubleshooting
- If a container fails to start, inspect logs with `docker logs <container-id>`.
- If you get port conflicts, check `server.port` in the service's `application.properties` or the `docker-compose.yml` mappings.

## Next steps / suggestions
- Add health check endpoints and readiness/liveness probes for production deployments.
- Add `README.md` files per service (already present) describing endpoints and data shapes.
