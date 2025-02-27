# Book Management REST API

A Spring Boot application that provides REST API endpoints for managing books, built with Java and adhering to best practices for service-oriented architecture.

## Project Overview

This project implements a RESTful API for book management with complete CRUD operations. The architecture follows a clean separation of concerns with distinct layers:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and transformations
- **Repository Layer**: Manages data persistence
- **Domain Layer**: Defines the data models

## Technologies Used

- **Java**: Core programming language
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data access abstraction
- **Hibernate**: ORM for database interactions
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for test-driven development
- **Maven/Gradle**: Dependency management and build automation

## Key Features

- Complete CRUD operations for Book resources
- Clean separation between API models and database entities
- Test-driven development approach with thorough unit testing
- RESTful design following best practices
- Builder pattern for clean object construction
- Dependency injection for loose coupling

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven or Gradle
- Your favorite IDE (IntelliJ, Eclipse, VS Code)

### Running the Application

1. Clone this repository
2. Navigate to the project root
3. Run `mvn spring-boot:run` or `./gradlew bootRun`
4. The API will be available at `http://localhost:8080`

## API Testing

The API endpoints can be tested using Postman. A Postman collection is included in the repository for convenience.

### Sample Endpoints

- `GET /api/books`: Retrieve all books
- `GET /api/books/{id}`: Retrieve a specific book
- `POST /api/books`: Create a new book
- `PUT /api/books/{id}`: Update an existing book
- `DELETE /api/books/{id}`: Delete a book

## Development Approach

This project follows Test-Driven Development (TDD) principles:

1. Write a failing test
2. Implement the minimum code to make the test pass
3. Refactor while maintaining test coverage

Mockito is used extensively to mock dependencies and ensure that each layer can be tested in isolation.

## Database Configuration

The application uses an H2 in-memory database by default for development and testing. For production deployment, configure your preferred database in the `application.properties` file.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── nilejackson/
│   │           └── books/
│   │               ├── domain/
│   │               │   ├── Book.java
│   │               │   └── BookEntity.java
│   │               ├── repositories/
│   │               │   └── BookRepository.java
│   │               ├── services/
│   │               │   ├── BookService.java
│   │               │   └── impl/
│   │               │       └── BookServiceImpl.java
│   │               └── BooksApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── nilejackson/
                └── books/
                    └── services/
                        └── impl/
                            └── BookServiceImplTest.java
```


