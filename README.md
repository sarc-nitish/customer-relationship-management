# CRM Backend (Spring Boot + MySQL + JWT)

This is the complete backend for your CRM project — built with Java 17, Spring Boot 3.3.4, MySQL, JWT authentication, and Swagger docs.

## Tech Stack

- Spring Boot (Web, Data JPA, Security, Validation)
- MySQL + Hibernate
- JWT (jjwt) for authentication
- BCrypt for password hashing
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Maven

## Project Structure

```
com.crm.project
├── config/          -> CorsConfig, SecurityConfig, SwaggerConfig
├── controller/       -> Auth, Customer, Lead, Deal, Task REST controllers
├── service/          -> Business logic
├── repository/       -> Spring Data JPA repositories
├── entity/           -> User, Customer, Lead, Deal, Task, ActivityLog, Role
├── dto/              -> Request/response objects
├── exception/        -> Global exception handler + custom exceptions
└── security/         -> JWT util, JWT filter, UserDetailsService
```

## Setup Steps

### 1. MySQL Database
MySQL should already be installed. The database will be created automatically (`createDatabaseIfNotExist=true`) — MySQL service just needs to be running.

Update your MySQL username/password in `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 2. Run the project

```bash
mvn clean install
mvn spring-boot:run
```

Or run `CrmApplication.java` directly from your IDE (IntelliJ/Eclipse).

### 3. Swagger UI

Open in browser:
```
http://localhost:8080/swagger-ui.html
```

All APIs will be listed here, and you can test them directly.

## API Flow

### Auth (public, no token required)
- `POST /api/auth/register` → create a new user (name, email, password, role)
- `POST /api/auth/login` → log in, response includes a JWT token

### Protected APIs (require `Authorization: Bearer <token>` header)
- `GET/POST/PUT/DELETE /api/customers`
- `GET/POST/PUT/DELETE /api/leads`
- `GET/POST/PUT/DELETE /api/deals`
- `GET/POST/PUT/DELETE /api/tasks`

### How to add a token in Swagger
1. First call `/api/auth/login`, you'll get a token in the response
2. Click the "Authorize" button in the top-right of Swagger UI
3. Paste `Bearer <your-token>`
4. Now you can test all the protected APIs

## Sample Request Bodies

**Register:**
```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "password": "password123",
  "role": "ADMIN"
}
```

**Login:**
```json
{
  "email": "rahul@example.com",
  "password": "password123"
}
```

**Create Customer:**
```json
{
  "name": "Acme Corp",
  "company": "Acme Pvt Ltd",
  "phone": "9876543210",
  "email": "contact@acme.com",
  "status": "NEW"
}
```

**Create Lead:**
```json
{
  "customerId": 1,
  "source": "WEBSITE",
  "stage": "NEW",
  "value": 50000
}
```

**Create Deal:**
```json
{
  "customerId": 1,
  "amount": 100000,
  "stage": "NEGOTIATION",
  "closeDate": "2026-12-31"
}
```

**Create Task:**
```json
{
  "title": "Follow up call",
  "description": "Discuss pricing",
  "dueDate": "2026-09-15T10:00:00",
  "status": "PENDING",
  "assignedToId": 1,
  "customerId": 1
}
```

## Frontend Integration

Your friend's frontend will send requests to `http://localhost:8080/api` (or `http://<your-ip>:8080/api` on the same WiFi). Don't forget to add your friend's origin in `CorsConfig.java`.

Every protected request needs this header:
```
Authorization: Bearer <token>
```

## Next Steps / TODO

- Role-based access control (e.g. only ADMIN can delete a customer) — add `@PreAuthorize` annotations
- Add pagination and filtering to the APIs (`Pageable`)
- Automatic activity log tracking (create a log entry whenever a customer is updated)
- Add unit tests (JUnit + Mockito)
- For production, move the JWT secret to an environment variable instead of hardcoding it in application.properties
