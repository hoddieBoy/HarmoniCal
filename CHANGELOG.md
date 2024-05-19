markdown
## 2024-05-19

### Added
- Use cases for creating, deleting, reading single, and reading list of events.
- Domain exceptions for invalid date format, invalid ID format, and resource not found.
- Global exception handler and controllers for event operations.
- Validation annotations and mappers for request/response handling.
- CI workflows for backend and common tasks, including path checker and labeler.
- Tests for use cases, domain models, repository adapters, and controllers.

### Changed
- Refactored `Event` class and added `EventRepository` interface.
- Updated Dockerfile for multi-stage builds and added docker-compose configurations.
- Updated Maven configuration with project metadata, dependencies, and profiles.