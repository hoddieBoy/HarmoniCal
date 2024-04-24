FROM eclipse-temurin:21-jdk-alpine AS builder

# Install dependencies:
# - inotify-tools: for watching file changes
# - dos2unix: for converting files from Windows to Unix format
# - curl: for downloading files
# - jq: for parsing JSON files
RUN apk add --no-cache inotify-tools dos2unix curl jq

# Define the working directory
WORKDIR /app

# Copy maven wrapper files
COPY mvnw .
COPY .mvn .mvn

RUN dos2unix mvnw && chmod +x mvnw

# Copy the project files
COPY pom.xml ./

# Download the dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src/ ./src/

FROM builder AS development

COPY docker/entrypoint.sh /usr/local/bin/entrypoint.sh

# Make the entrypoint script executable
RUN dos2unix /usr/local/bin/entrypoint.sh && chmod +x /usr/local/bin/entrypoint.sh

# Install bash for the entrypoint script
RUN apk add --no-cache bash

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
