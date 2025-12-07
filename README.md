# Buggy Task Manager

A Spring Boot task management API with intentional bugs for interview debugging exercises.

## Features

- CRUD operations for tasks
- Task prioritization (HIGH, MEDIUM, LOW)
- Completion tracking
- Audit logging for task changes
- Input validation

## Known Issues

Users have reported several issues:

1. Getting task details by ID sometimes fails with server errors
2. Tasks can be created with empty titles or past due dates
3. Updating a task sometimes resets its completion status
4. When multiple users complete tasks simultaneously, counts are inaccurate

## Setup

### Prerequisites

- Java 17+
- Maven 3.8+

# Task Management API

## Access Points

| Service             | URL                                                                            | Credentials                                                                               | Purpose                       |
| ------------------- | ------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------- | ----------------------------- |
| Application         | [http://localhost:8080](http://localhost:8080)                                 | N/A                                                                                       | Main application              |
| H2 Database Console | [http://localhost:8080/h2-console](http://localhost:8080/h2-console)           | **JDBC URL:** `jdbc:h2:mem:testdb`<br>**Username:** `sa`<br>**Password:** *(leave empty)* | View and query database       |
| Swagger UI          | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | N/A                                                                                       | Interactive API documentation |
| OpenAPI Spec        | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)         | N/A                                                                                       | Raw OpenAPI specification     |

---

## Simulate Race Condition Script

To test concurrent updates, you can use the provided shell script:

```bash
#!/bin/bash
TASK_ID=$1
ENDPOINT="http://localhost:8080/api/tasks/$TASK_ID/complete"

# Send 10 concurrent requests
for i in {1..10}; do
  curl -X PATCH "$ENDPOINT" &
done

wait
echo "All requests completed"
```

### Make the script executable

```bash
chmod +x ./simulate_race.sh
```

### Run the script (example for task ID = 67)

```bash
./simulate_race.sh 67
```

## API Endpoints

### Task Management

| Method                                                                                                    | Endpoint                         | Description                                     | Request Body Example |
| --------------------------------------------------------------------------------------------------------- | -------------------------------- | ----------------------------------------------- | -------------------- |
| GET                                                                                                       | `/api/tasks`                     | Get all tasks                                   | N/A                  |
| GET                                                                                                       | `/api/tasks/{id}`                | Get task by ID                                  | N/A                  |
| GET                                                                                                       | `/api/tasks/completed`           | Get completed tasks                             | N/A                  |
| GET                                                                                                       | `/api/tasks/priority/{priority}` | Get tasks by priority (`HIGH`, `MEDIUM`, `LOW`) | N/A                  |
| POST                                                                                                      | `/api/tasks`                     | Create new task                                 | ```json              |
| { "title": "Task title", "description": "Task description", "dueDate": "2024-12-31", "priority": "HIGH" } |                                  |                                                 |                      |

````|
| PUT | `/api/tasks/{id}` | Update existing task | ```json
{ "title": "Updated title", "description": "Updated description", "dueDate": "2024-12-25", "priority": "MEDIUM" }
``` |
| PATCH | `/api/tasks/{id}/complete` | Mark task as completed | N/A |

---

### Notes
- Ensure the application is running on port **8080** before accessing the services.
- The H2 console is enabled only for in-memory development mode.
- Swagger UI provides a convenient way to test endpoints interactively.

---

````
