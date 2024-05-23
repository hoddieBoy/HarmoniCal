# Harmonical Backend

## Overview

The Harmonical Backend is a Spring Boot application designed to manage events. It provides APIs for creating, reading,
updating, and deleting events, along with handling various domain-specific exceptions and validations. The backend is
built with a focus on clean architecture, separation of concerns, and testability.

## Features

- Event Management: Create, read, update, and delete events.
- Validation: Ensures data integrity with custom validation annotations.
- Exception Handling: Global exception handling for consistent error responses.
- Persistence: Uses JPA for database interactions.
- Testing: Comprehensive unit and integration tests.
- Docker Support: Multi-stage Dockerfile for building and running the application.
- CI/CD: Configured with CI workflows for backend tasks.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Development](#development)
    - [Running Tests](#running-tests)
    - [Code Quality](#code-quality)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

- Java 21
- Docker
- Maven

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/harmonical-backend.git
   cd harmonical-backend
   ```

2. Build the Docker images:
   ```sh
   docker compose -f docker-compose.yml build
   ```

### Running the Application

1. Start the application:
   ```sh
   docker compose up -d
   ```

2. The application will be available at `http://localhost:8081`.

## API Documentation

The backend provides the following endpoints:

- **Create Event**: `POST /api/events`
- **Read Event**: `GET /api/events/{eventId}`
- **Read List of Events**: `GET /api/events`
- **Delete Event**: `DELETE /api/events/{eventId}`

### Example Requests

#### Create Event

```sh
curl -X POST http://localhost:8081/api/events \
  -H "Content-Type: application/json" \
  -d '{
        "event_title": "Board Meeting",
        "event_description": "Monthly strategic meeting",
        "event_start_date": "2024-06-01",
        "event_start_time": "12:00:00",
        "event_duration": "2h30m",
        "event_location": "Conference Room A"
      }'
```

#### Read Event

```sh
curl -X GET http://localhost:8081/api/events/{eventId}
```

#### Read List of Events

```sh
curl -X GET http://localhost:8081/api/events
```

#### Delete Event

```sh
curl -X DELETE http://localhost:8081/api/events/{eventId}
```

## Configuration

Configuration is managed through `application.yml` files located in the `src/main/resources` and `src/test/resources`
directories.

### Environment Variables

- `DB_HOST`: Database host
- `DB_PORT`: Database port
- `DB_NAME`: Database name
- `DB_USER`: Database user
- `DB_PASSWORD`: Database password
- `SERVER_PORT`: Server port

## Development

### Running Tests

To run tests, use the following command:

```sh
docker compose -f docker-compose.test.yml up --abort-on-container-exit
```

### Code Quality

The project uses `sonarcloud` for code quality checks. To run the code quality checks locally, use the following
command:

```sh
mvn clean verify sonar:sonar -Dsonar.projectKey=harmonical-backend -Dsonar.organization=yourorganization -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=yourtoken
```

## Deployment

The application can be deployed using Docker. The `Dockerfile` is configured for multi-stage builds to create optimized
production images.

### Production Deployment

1. Build the production image:
   ```sh
   docker build -t harmonical-backend:latest --target production .
   ```

2. Run the production container:
   ```sh
   docker run -d -p 8080:8080 harmonical-backend:latest
   ```

## Contributing

Contributions are welcome! Please follow these steps mentioned in [CONTRIBUTING.md](../docs/CONTRIBUTING.md).

## License

This project is licensed under the MIT License. See the [LICENSE](../LICENSE) file for details.

