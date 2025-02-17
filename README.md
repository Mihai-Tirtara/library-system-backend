# Library Management System Backend

This is the backend service for the Library Management System, built with Spring Boot. The service provides RESTful endpoints for managing books and includes AI-powered book insights generation.

## Features

- **Book Management**: Complete CRUD operations for books
- **Search Functionality**: Search books by title and/or author
- **AI Integration**: Generate insights for books using OpenAI
- **Data Validation**: Comprehensive input validation
- **Error Handling**: Global exception handling with meaningful error messages
- **Database**: H2 in-memory database (can be easily switched to other databases)
- **Testing**: Comprehensive integration tests

## Prerequisites

- Java 17 or higher
- Gradle
- OpenAI API key (for AI insights feature)

## Setup & Installation

1. Clone the repository:
```bash
git clone [your-repository-url]
cd library-system
```

2. Set up your OpenAI API key:
    - Create an environment variable:
      ```bash
      # For Windows
      set OPENAI_API_KEY=your-api-key-here
      
      # For Unix/Linux/MacOS
      export OPENAI_API_KEY=your-api-key-here
      ```
    - Or update `application.properties`:
      ```properties
      spring.ai.openai.api-key=your-api-key-here
      ```

3. Build the project:
```bash
./gradlew build
```

4. Run the application:
```bash
./gradlew bootRun
```

The server will start on `http://localhost:8080`

## API Endpoints

### Books

- **Create a Book**
    - `POST /api/books`
    - Request body example:
      ```json
      {
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "isbn": "9780743273565",
        "publicationYear": 1925,
        "description": "A story of the fabulously wealthy Jay Gatsby"
      }
      ```

- **Get All Books**
    - `GET /api/books`

- **Get Book by ID**
    - `GET /api/books/{id}`

- **Update Book**
    - `PUT /api/books/{id}`
    - Request body similar to Create

- **Delete Book**
    - `DELETE /api/books/{id}`

- **Search Books**
    - `GET /api/books/search?title={title}&author={author}`
    - Both parameters are optional

- **Get AI Insights**
    - `GET /api/books/{id}/ai-insights`

## Running Tests

Execute all tests:
```bash
./gradlew test
```
