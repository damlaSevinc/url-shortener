# URL Shortener Project

A simple URL shortener service that takes a long URL, generates a short hash, and stores the mapping. The service provides endpoints to shorten URLs and resolve them back to their original form.

## Features

- Shortens long URLs into a short hash.
- Resolves short URL back to the original URL.
- Caches short URLs in Redis for faster access.
- Stores URL mappings in an H2 database.
- 7-Character Hashing for URL Shortening
- Scalability with UUID-Based Hashing
- Unit and Integration Testing

## Technology Stack

- **Backend**: Java with Spring Boot
- **Database**: H2 (for persistent storage of URLs)
- **Caching**: Redis (for caching URL mappings)
- **Test Framework**: JUnit, Mockito for unit and integration testing

## Installation and Setup

### Prerequisites

- JDK 17 or higher (<https://www.oracle.com/java/technologies/javase-jdk17-downloads.html>)
- Maven (<https://maven.apache.org/>)
- H2 Database (Embedded) (<https://www.h2database.com/html/main.html>)
- Redis (Optional, for caching) (<https://redis.io/>)

### Steps to Run

#### Clone the repository

```bash
git clone https://github.com/damlaSevinc/url-shortener.git
cd url-shortener
```

#### Set up Redis (Optional for caching)

- Ensure Redis is running locally on port 6379, or configure it in application.properties.
- docker-compose.yml file is provided to run Redis in a Docker container.

#### Build and run the project

```bash
mvn clean install

mvn spring-boot:run
```

### Test the project

```bash
mvn test
```

Also, following CURL commands can be used to test custom v:

```bash
CURL -X POST http://localhost:8080/api/urls \
    -H "Content-Type: application/json" \
    -d '{"originalUrl": "https://www.example.com"}'
```

or

```bash
CURL -X GET http://localhost:8080/api/urls/e15adb8
```

## API Endpoints

Related info about the API endpoints can be found in the [API Documentation](API_Documentation.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
