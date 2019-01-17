# Pet App REST Service

This document presents a complete implementation of the Disney coding challenge. The original readme has been moved to README-instructions.md. It is an example of what I can do in 24 hours. 

## Design

The application is written in Java, leverages Spring Boot and Spring Data JPA/Hibernate, uses Gradle for the build system, Docker for containerization, Swagger for API docs, and REST Assured for testing. 

Rather than relying on an in-memory database, I use a Dockerized version of PostgreSQL to store persistent data. This is superior to an in-memory database since PostgreSQL is a likely production candidate. It therefore minimizes differences between development and production use. 

In addition, I use Docker Compose to launch the application as a containerized REST service and PostgreSQL database.

Given additional time, a number of items could be further cleaned up, such as: 
1. Basic HTTP Auth for validating clients with a client ID and shared secret. 
2. A dedicated error handler for customizing JSON errors returned to the client. 
3. Centralizing configuration. 
4. Profiles for different environments. 
5. Suppressing internal Swagger endpoints. 
6. Suppressing development Actuator endpoints. 
7. Additional tests.

## How to build

I provided a convenience BASH script for installing packages useful for building/testing this project. See bin/env-setup.sh. I use SDKMAN and Homebrew package managers, so the script is limited to use on MacOS.

In order to simplify building and testing, I provided a Makefile that wraps all Gradle and Docker commands. 

To build: 

```
make clean build
```

To run tests (spins up temporary Dockerized PostgreSQL database and REST service):

```
make test
```

To launch a complete Dockerized application using Docker Compose:

```
make docker-up
```

To launch a Dockerized database only with a native REST service:

```
make run
```

Once launched, the REST service is accessible at http://localhost:8080/ and the Swagger dashboard is available at http://localhost:8080/swagger-ui.html. 


Best regards, Chris
<p>Contact: (310) 428-4312
