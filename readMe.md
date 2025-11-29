# Entrolytics Notifier

**Entrolytics Notifier** is a command-line Spring Boot application designed to send notifications based on a JSON input file. The application supports validation, retry with circuit breaker, and structured logging for monitoring.

---

## Features

1. **Command-line Spring Boot Application**  
   Runs based on a JSON input file passed as an argument.

2. **Global Exception Handling**  
   All exceptions during processing are logged in a structured JSON format for easier monitoring.

3. **POJO Validation**  
   Incoming request messages are validated automatically. Invalid messages trigger notification failure logs.

4. **WebClient Requests with Retry & Circuit Breaker**  
   HTTP calls are made with:
    - 3 retries on failure
    - Circuit breaker with 10-second timeout

5. **Unit Testing**
    - Two test cases validate application behavior.
    - Uses `CapturedOutput` to verify logs and notification status.

6. **Structured Logging**
    - Logback is configured with a standard logging pattern for clarity.
    - Errors are logged as structured JSON.

7. **Clean Architecture**
    - Packages are separated by responsibility.
    - Interfaces are used for better maintainability.

8. **Gradle Build**
    - The project uses Gradle to manage dependencies, build, and run tests.

9. **Linting**
    - Linting can be added to the Gradle build pipeline (TODO).

---

## Prerequisites

- Java 21+
- Gradle 8+

---

## Build and Run

### 1. Using Pre-Built JAR

The pre-built JAR is located in the `resources` folder. Run it with:

```bash
java -jar src/main/resources/entrolytics-notifier-0.0.1-SNAPSHOT-plain.jar notifyFile=/path/to/file.json


# Clean and build the JAR
./gradlew clean build

# The JAR will be created at:
# build/libs/entrolytics-notifier-0.0.1-SNAPSHOT-plain.jar

# Run the generated JAR
java -jar build/libs/entrolytics-notifier-0.0.1-SNAPSHOT-plain.jar notifyFile=/path/to/file.json

# To execute the unit tests:
./gradlew test
