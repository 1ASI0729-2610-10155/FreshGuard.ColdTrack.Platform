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

## Production URLs

| Resource | URL |
|---|---|
| Backend API | `https://freshguard-coldtrack-api.onrender.com/api/v1` |
| Swagger UI | `https://freshguard-coldtrack-api.onrender.com/swagger-ui/index.html` |
| OpenAPI JSON | `https://freshguard-coldtrack-api.onrender.com/v3/api-docs` |
| Frontend | `https://coldtrack-front-open.web.app` |

The production backend is deployed as a Render Web Service using Docker. The production database is a MySQL 8 instance hosted in Filess.io.

## Local Configuration

Copy the values from `.env.example` into environment variables. The development defaults expect MySQL on `localhost:3306` with user `root`, password `password`, and database `freshguard_coldtrack`.

```powershell
$env:JAVA_HOME='C:\Program Files\Eclipse Adoptium\jdk-26.0.1.8-hotspot'
$env:DATABASE_USER='root'
$env:DATABASE_PASSWORD='password'
./mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080`. Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## Render Environment Variables

Use the following variable names in Render. Do not commit real passwords or JWT secrets.

| Variable | Purpose |
|---|---|
| `SPRING_PROFILES_ACTIVE=prod` | Enables production MySQL configuration. |
| `DATABASE_URL` | MySQL host, for example the Filess.io host. |
| `DATABASE_PORT` | MySQL port. |
| `DATABASE_NAME` | MySQL database name. |
| `DATABASE_USER` | MySQL user. |
| `DATABASE_PASSWORD` | MySQL password. |
| `DATABASE_MAX_POOL_SIZE=1` | Keeps the connection pool compatible with free/shared MySQL limits. |
| `DATABASE_MIN_IDLE=0` | Avoids idle connections while Render is inactive. |
| `JWT_SECRET` | Strong secret used to sign JWT tokens. |
| `FRONTEND_ORIGINS` | Allowed frontend origins, e.g. `https://coldtrack-front-open.web.app,http://localhost:4200`. |
| `OPENAPI_SERVERS` | Swagger server list, e.g. `https://freshguard-coldtrack-api.onrender.com,http://localhost:8080`. |

Render free instances may spin down after inactivity. The first request after idle time can take several seconds while the service wakes up.

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

## Sprint Review Flow

1. Open the frontend at `https://coldtrack-front-open.web.app`.
2. Sign in with the demo account or create a new account.
3. Register a shipment from the frontend.
4. Register a sensor.
5. Link the available sensor to a registered or in-transit shipment.
6. Add a telemetry reading for the assigned sensor.
7. Confirm that the dashboard updates shipment status, temperature, humidity, and alerts.
8. Complete or cancel the shipment from the shipment details view.
9. Open Swagger UI to verify the same resources through the deployed backend API.

## Verification

```powershell
./mvnw.cmd clean test
```

For a deployment-oriented build check:

```powershell
./mvnw.cmd -DskipTests package
```

## Git Flow

Recommended release workflow:

```text
main -> develop -> feature/<scope> -> develop -> main -> tag/release
```

Feature branches should be merged back into `develop`. Release-ready changes are then merged from `develop` into `main` and tagged.
