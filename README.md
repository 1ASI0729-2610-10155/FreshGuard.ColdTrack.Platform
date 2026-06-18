# FreshGuard ColdTrack Platform

FreshGuard ColdTrack Platform is the internal RESTful backend for ColdTrack, a cold-chain shipment monitoring solution created by the HackRats team. It manages authentication, shipments, IoT sensors, telemetry, alerts, analytics, profiles, and public engagement resources.

## Author

Developed by the HackRats team for FreshGuard Technologies.

## Technology Stack

- Java 26 and Spring Boot 4.0.6
- Maven Wrapper
- Spring Web, Validation, Security, WebSocket, and Data JPA
- MySQL 8 for development and production
- JWT bearer authentication with BCrypt password hashing
- OpenAPI 3 and Swagger UI
- H2 for automated tests
- Docker multi-stage build

## Architecture

The codebase follows Domain-Driven Design and CQRS-oriented conventions from the course reference projects. It is divided into the bounded contexts `iam`, `profiles`, `logistics`, `monitoring`, `alerting`, `analytics`, and `engagement`, plus a `shared` kernel.

## Requirements

- Eclipse Temurin JDK 26
- MySQL 8
- Docker, optional

## Local Configuration

Copy the values from `.env.example` into environment variables. The development defaults expect MySQL on `localhost:3306` with user `root`, password `password`, and database `freshguard_coldtrack`.

```powershell
$env:JAVA_HOME='C:\Program Files\Eclipse Adoptium\jdk-26.0.1.8-hotspot'
$env:DATABASE_USER='root'
$env:DATABASE_PASSWORD='password'
./mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080`. Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## Demo Account

- Email: `test@test.com`
- Password: `password`

The password is stored only as a BCrypt hash.

## Main Endpoints

| Area | Endpoints |
|---|---|
| Authentication | `POST /api/v1/authentication/sign-up`, `POST /api/v1/authentication/sign-in` |
| IAM | `GET /api/v1/users`, `GET /api/v1/roles` |
| Logistics | `GET/POST /api/v1/shipments`, shipment lifecycle action endpoints |
| Monitoring | `GET/POST /api/v1/sensors`, `POST /api/v1/telemetry-readings` |
| Alerts | `GET /api/v1/alerts`, acknowledgement and resolution endpoints |
| Analytics | `GET /api/v1/analytics/dashboard`, `GET /api/v1/reports/shipments/{shipmentCode}` |
| Engagement | `POST /api/v1/contact-messages`, `GET /api/v1/testimonials` |

## Verification

```powershell
./mvnw.cmd clean test
```

No deployment configuration has been executed and the frontend has not been modified in this phase.
