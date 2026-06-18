# ColdTrack Platform Technical Stories

## TS-IAM-001 - Register a user

As a frontend developer, I want to register users through `POST /api/v1/authentication/sign-up` so that ColdTrack can create secure role-based accounts.

Acceptance criteria: valid data returns `201`; duplicate e-mail returns `409`; invalid data returns `400`; passwords are stored as BCrypt hashes.

## TS-IAM-002 - Authenticate a user

As a frontend developer, I want to authenticate through `POST /api/v1/authentication/sign-in` so that the application receives a JWT bearer token.

Acceptance criteria: valid credentials return `200` with a token; unknown users return `404`; invalid credentials return `409`.

## TS-LOG-001 - Manage shipment lifecycle

As a frontend developer, I want to register, list, start, complete, and cancel shipments so that logistics operations are traceable.

## TS-MON-001 - Receive telemetry

As an IoT integrator, I want to post sensor readings so that temperature and humidity are stored, published through WebSocket, and evaluated for anomalies.

## TS-ALT-001 - Generate and manage alerts

As a frontend developer, I want to list, acknowledge, and resolve generated alerts so that incidents have a controlled lifecycle.

## TS-ANA-001 - Query dashboard and reports

As a frontend developer, I want aggregated metrics and shipment report projections so that dashboards and historical reports can be rendered.

## TS-I18N-001 - Localized errors

As an API consumer, I want error messages selected by `Accept-Language` so that English is the default and Latin American Spanish is supported.
