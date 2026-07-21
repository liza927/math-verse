# MathVerse
**Java EE Diploma Project**
TeachMeSkills

## Project Overview

Interactive online platform for learning mathematics. Combines a classic lecture library, an adaptive math problem generator, and student/teacher analytics.

Main features:
- registers and authenticates users via JWT (STUDENT / TEACHER roles)
- teacher creates topics and task templates (operation, complexity, generation parameters)
- generates math problems on the fly for a chosen task template (matrix multiplication, transpose, determinant, inverse matrix)
- checks a student's submitted answer against the generated correct answer
- stores every attempt and computes per-topic statistics (correct % per topic) for a student
- aggregates the same statistics across all students for the teacher dashboard
- logs every controller call (method, arguments, result) via AOP
- documents the whole API with Swagger/OpenAPI

## Features

- JWT authentication and role-based access (`Spring Security`)
- Strategy pattern for task generation — one class per math operation, selected at runtime by a factory
- Database schema managed by Flyway migrations (not Hibernate auto-DDL)
- Global exception handler returning a unified error DTO (`message`, `status`, `timestamp`)
- AOP logging of controller calls (`@Around` advice)
- Student analytics via Java Stream API (`Collectors.groupingBy`)
- Unit tests for the service layer and generators (JUnit 5, Mockito, AssertJ)
- Full Docker support (application + PostgreSQL)

## Tech Stack

- Java 17
- Spring Boot 3.2.4: Web, Data JPA, Security, AOP
- Hibernate 6 / PostgreSQL 15
- Flyway (database migrations)
- JWT: `io.jsonwebtoken` (jjwt) 0.11.5
- Lombok
- springdoc-openapi (Swagger UI)
- JUnit 5, Mockito, AssertJ
- Maven (multi-module project)
- Docker / Docker Compose

Layered structure (`math-core-platform`):
`entity` (User, Topic, TaskTemplate, Attempt, Role, Operation),
`repository` (Spring Data JPA interfaces),
`service` (UserService, TopicService, TaskTemplateService, AttemptService),
`controller` (AuthController, TopicController, TaskTemplateController, AttemptController),
`dto` (request/response objects),
`security` (JwtService),
`config` (SecurityConfig, RequestFilter),
`generator` (TaskGenerator interface + 4 implementations + TaskGeneratorFactory),
`aop` (LoggingAspect),
`exception` (GlobalExceptionHandler)

## Installation & Running

```
git clone https://github.com/liza927/math-verse
cd math-verse
docker-compose up -d
```

This starts two containers: `postgres` (database) and `math-core-app` (the API, built from `math-core-platform/Dockerfile`). Flyway applies all migrations automatically on first startup.

The API is available at `http://localhost:8080`.

## Usage

Swagger UI (interactive API docs):
```
http://localhost:8080/swagger-ui/index.html
```

Typical flow:
1. `POST /api/auth/register` -> create a STUDENT account
2. `POST /api/auth/login` -> get a JWT token
3. (as TEACHER) `POST /api/teacher/topics`, `POST /api/teacher/task-templates` -> set up content
4. `POST /api/attempts/start` -> get a generated task
5. `POST /api/attempts/submit` -> submit the answer, get `correct: true/false`
6. `GET /api/attempts/stats` -> student's own topic statistics
7. (as TEACHER) `GET /api/attempts/teacher/stats` -> statistics across all students

## Request examples

Register:
```
POST /api/auth/register
{"email": "test@example.com", "password": "qwerty123"}
```

Start an attempt (requires `Authorization: Bearer <token>`):
```
POST /api/attempts/start
{"taskTemplateId": 1}
```

## Testing

```
cd math-core-platform
mvn test
```

## Author

https://github.com/liza927

Course: Java EE, TeachMeSkills (2025-2026)