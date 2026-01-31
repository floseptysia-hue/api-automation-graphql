# API Automation Testing – GraphQL

This repository contains API automation tests for a **GraphQL service** built using **Java**.  
The tests focus on validating GraphQL queries and mutations, as well as verifying response data and error handling for regression purposes.

---

## API Under Test

**API Type**  
GraphQL

**Endpoint**  
https://lmsb2b.do.dibimbing.id/graphql

---

## Automation Scope

- GraphQL API automation testing
- Validation of queries and mutations
- Response data and error handling verification
- Regression-ready test scenarios

---

## Tech Stack

- Java
- GraphQL API Testing
- Maven
- Allure Report
- GitHub Actions (CI/CD)

---

## Project Structure

src
└── test
└── java
├── api # GraphQL API test cases
└── utils # Helper and configuration classes


---

## Running the Tests

### Run all API tests
```bash
mvn clean test
mvn allure:report
mvn allure:serve
