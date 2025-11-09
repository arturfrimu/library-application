# Library System

**Stack**: Java 24, Spring Boot 3.5, Spring MVC, Spring Data JPA, Hibernate Envers, PostgreSQL, Flyway, MapStruct, Lombok, OpenAPI, Testcontainers, Docker Compose.

> Library management system with books, users, and book borrowing/returning functionality.

---

## Quick Start

**Requirements**: Docker Compose

```bash
# Start application using make
make up
```

```bash
# Start application using docker
docker compose up -d
```

**Verification**:
```bash
curl http://localhost:8092/actuator/health
```

```bash
curl http://localhost:8092/api/books?size=10&page=0
```

---

## API Documentation

OpenAPI specifications:
- [Books API](api/books.yaml)
- [Borrows API](api/borrows.yaml)
- [Users API](api/users.yaml)

### Endpoints

**Users API:**
- Get all users: `GET http://localhost:8092/api/users`

**Books API:**
- Get all books: `GET http://localhost:8092/api/books`
- Create a book: `POST http://localhost:8092/api/books`

**Borrows API:**
- Borrow a book: `POST http://localhost:8092/api/borrows`
- Return a book: `POST http://localhost:8092/api/borrows/return`
- Get borrowed books for a user: `GET http://localhost:8092/api/borrows/users/{userId}`

---

## Author

* Artur Frimu
* [Github](https://github.com/arturfrimu)
