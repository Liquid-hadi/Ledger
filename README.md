# 💰 Personal Finance Tracker

A RESTful API built with **Spring Boot 4.1** and **Java 25** to track personal income, expenses, and monthly financial summaries.

---

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java_25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_4.1-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

---

## ⚙️ Setup

### Prerequisites
- Java 25
- Maven
- PostgreSQL

### 1. Create the database
```sql
CREATE DATABASE financedb;
CREATE SCHEMA my_finance;
```

### 2. Configure `application.properties`
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

### 3. Run
```bash
mvn spring-boot:run
```

---

## 🔌 API Endpoints

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/categories` | Get all categories |
| `GET` | `/api/categories/{id}` | Get category by ID |
| `POST` | `/api/categories` | Create a category |
| `PUT` | `/api/categories/{id}` | Update a category |
| `DELETE` | `/api/categories/{id}` | Delete a category |

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
| `GET` | `/api/transactions` | Get all transactions |
| `GET` | `/api/transactions/{id}` | Get transaction by ID |
| `POST` | `/api/transactions` | Create a transaction |
| `PUT` | `/api/transactions/{id}` | Update a transaction |
| `DELETE` | `/api/transactions/{id}` | Delete a transaction |
| `GET` | `/api/transactions/summary?year=2024&month=3` | Monthly summary |

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

**Monthly summary response**
```json
{
  "year": 2024,
  "month": 3,
  "totalIncome": 5000.00,
  "totalExpenses": 2340.75,
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
