# 💰 Personal Finance Tracker

A full-stack personal finance application to track income, expenses, and monthly financial summaries — built with **Spring Boot 4.1** on the backend and **Angular 19** on the frontend.

---

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java_25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_4.1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Angular](https://img.shields.io/badge/Angular_19-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)

---

## ⚙️ Backend Setup

### Prerequisites
- Java 25
- Maven
- Docker

### 1. Start PostgreSQL via Docker
```bash
docker run --name financedb -e POSTGRES_PASSWORD=yourpassword -p 5432:5432 -d postgres
```

### 2. Create the database and schema
```sql
CREATE DATABASE financedb;
CREATE SCHEMA my_finance;
```

### 3. Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financedb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.default_schema=my_finance
server.port=8080
```

### 4. Run
```bash
mvn spring-boot:run
```

API docs available at: `http://localhost:8080/swagger-ui/index.html`

---

## 🖥 Frontend Setup

### Prerequisites
- Node.js
- Angular CLI

### 1. Install dependencies
```bash
cd frontend
npm install
```

### 2. Run
```bash
ng serve
```

App runs at: `http://localhost:4200`

---

## 🔌 API Endpoints

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/categories` | Get all active categories |
| `GET` | `/api/categories/{id}` | Get category by ID |
| `POST` | `/api/categories` | Create a category |
| `PUT` | `/api/categories/{id}` | Update a category |
| `DELETE` | `/api/categories/{id}` | Soft delete a category |

**Request body**
```json
{
  "name": "Groceries",
  "type": "EXPENSE"
}
```

---

### Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/transactions` | Get transactions (paginated, filterable) |
| `GET` | `/api/transactions/{id}` | Get transaction by ID |
| `POST` | `/api/transactions` | Create a transaction |
| `PUT` | `/api/transactions/{id}` | Update a transaction |
| `DELETE` | `/api/transactions/{id}` | Soft delete a transaction |

**Query parameters for GET `/api/transactions`**

| Param | Type | Description |
|-------|------|-------------|
| `type` | `INCOME` \| `EXPENSE` | Filter by type |
| `categoryId` | `number` | Filter by category |
| `startDate` | `yyyy-MM-dd` | Filter from date |
| `endDate` | `yyyy-MM-dd` | Filter to date |
| `page` | `number` | Page number (default: 0) |
| `size` | `number` | Page size (default: 15) |

**Request body**
```json
{
  "description": "Weekly groceries",
  "amount": 85.50,
  "type": "EXPENSE",
  "date": "2024-03-15",
  "categoryId": 1,
  "notes": "Bought vegetables"
}
```

**Paginated response**
```json
{
  "transactions": [...],
  "currentPage": 0,
  "totalPages": 3,
  "totalElements": 42,
  "size": 15
}
```

---

### Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/summary` | Get financial summary for a date range |

**Query parameters**

| Param | Type | Description |
|-------|------|-------------|
| `startDate` | `yyyy-MM-dd` | Start of date range |
| `endDate` | `yyyy-MM-dd` | End of date range |

**Response**
```json
{
  "startDate": "2026-01-22",
  "endDate": "2026-02-21",
  "totalIncome": 5000.00,
  "totalExpense": 2340.75,
  "netBalance": 2659.25
}
```

---

## 🧩 Enums

| Enum | Values |
|------|--------|
| `CategoryType` | `INCOME`, `EXPENSE` |
| `TransactionType` | `INCOME`, `EXPENSE` |
| `Status` | `ACTIVE`, `DELETED` |
