# MathVerse
**Java EE Diploma Project**  
TeachMeSkills (2025–2026)

## Project Overview

Interactive online platform for learning mathematics.  
Combines adaptive math problem generation, student progress tracking, teacher analytics and AI-powered hints.

### Main features
- JWT authentication with roles `STUDENT` / `TEACHER`
- Teacher creates topics and task templates
- On-the-fly generation of matrix problems (multiplication, transpose, determinant, inverse matrix)
- Answer checking and attempt history
- AI tutor hint after 3 consecutive wrong answers on the same topic (via isolated gateway + Google Gemini)
- Per-topic statistics for students
- Teacher dashboard (online students + difficult topics)
- AOP logging of controller calls
- Full API documentation with Swagger / OpenAPI
- Simple web frontend

## Tech Stack

| Layer            | Technology                          |
|------------------|-------------------------------------|
| Language         | Java 17                             |
| Framework        | Spring Boot 3.2.4                   |
| Security         | Spring Security + JWT (jjwt 0.11.5) |
| Persistence      | Spring Data JPA + Hibernate 6       |
| Database         | PostgreSQL 15                       |
| Migrations       | Flyway                              |
| Logging          | Log4j2 + Spring AOP                 |
| API Docs         | springdoc-openapi (Swagger UI)      |
| Build            | Maven (multi-module)                |
| Containers       | Docker / Docker Compose             |
| Tests            | JUnit 5, Mockito, AssertJ, JaCoCo   |
| AI Gateway       | Separate Spring Boot service        |

### Modules
- `math-core-platform` — main REST API + frontend
- `math-ai-gateway` — isolated service for Gemini AI hints
- `math-legacy-portal` — legacy servlet/JSP lecture library

## Requirements

- JDK 17+
- Maven 3.9+
- Docker & Docker Compose
- (optional) Google Gemini API key for AI hints

## How to Run

### Option 1 — Docker (recommended)

```bash
git clone https://github.com/liza927/math-verse
cd math-verse
docker-compose up -d --build
```

| Service        | URL                                              |
|----------------|--------------------------------------------------|
| API + Frontend | http://localhost:8080                            |
| Swagger UI     | http://localhost:8080/swagger-ui/index.html      |
| PostgreSQL     | localhost:5432                                   |
| AI Gateway     | http://localhost:8081                            |

### Option 2 — Locally
# 1. Start PostgreSQL
docker-compose up -d db

# 2. Start AI Gateway
cd math-ai-gateway
mvn spring-boot:run

# 3. Start main application
cd ../math-core-platform
mvn spring-boot:run
```

## Test Accounts

| Role    | Email                 | Password   |
|---------|-----------------------|------------|
| Teacher | teacher@mathverse.com | teacher123 |
| Student | register via API      | any (6–12 chars) |

## API Overview

### Auth
- `POST /api/auth/register` — create student
- `POST /api/auth/login` — get JWT + refresh token
- `POST /api/auth/refresh` — refresh access token

### Topics
- `POST /api/teacher/topics` — create topic (TEACHER)  
  Body: `{ "name": "Матрицы" }`
- `GET /api/topics` — list topics

### Task Templates
- `POST /api/teacher/task-templates` — create template (TEACHER)  
  Body:
  ```json
  {
    "topicId": 1,
    "operation": "MULTIPLY_TWO_MATRICES",
    "complexity": 5,
    "generationParam": "{}"
  }
  ```
  Available operations: `MULTIPLY_TWO_MATRICES`, `TRANSPOSE`, `FIND_DETERMINANT`, `FIND_INVERSE_MATRIX`

- `GET /api/task-templates` — list templates

### Attempts (Student)
- `POST /api/attempts/start` — generate task  
  Body: `{ "taskTemplateId": 5 }`
- `POST /api/attempts/submit` — submit answer  
  Body: `{ "attemptId": 12, "studentAnswer": "1, 2;3, 4;" }`
- `GET /api/attempts/stats` — personal statistics

### Teacher Dashboard
- `GET /api/teacher/dashboard` — online students + difficult topics

## Testing

```bash
cd math-core-platform
mvn test
```

Coverage report (JaCoCo):
```
math-core-platform/target/site/jacoco/index.html
Current coverage ≈ **87%**.

## Project Structure (`math-core-platform`)

```
com.mathverse.core
├── aop            # LoggingAspect
├── client         # AiGatewayClient
├── config         # Security, OpenAPI, DataInitializer
├── controller     # REST controllers
├── dto            # Request/Response objects
├── entity         # JPA entities
├── exception      # Custom exceptions + GlobalExceptionHandler
├── generator      # Strategy pattern for task generation
├── repository     # Spring Data JPA
├── security       # JwtService
└── service        # Business logic


## Postman

Import the collection from:

https://samuylikliza-5820445.postman.co/workspace/Liza-Samuylik's-Workspace~817dd587-3bc0-43cc-899e-534aad8d0520/collection/50612537-91723f38-de6a-46c6-b518-f1b34d12ef53?action=share&creator=50612537&active-environment=50612537-04074cdb-1d67-4293-b159-e4cc8cd60074


## Author

- GitHub: [liza927](https://github.com/liza927)
- Course: Java EE, TeachMeSkills (2025–2026)