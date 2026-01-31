# Web Automation Testing – Selenium (Java)

This repository contains web automation tests built using **Selenium WebDriver with Java**.  
The automation focuses on validating critical user flows and is structured using the **Page Object Model (POM)** to ensure maintainability and scalability.

---

## Automation Scope

- Automation testing for critical business flows
- Implementation of Page Object Model (POM)
- Designed for regression testing and CI/CD execution

---

## Web Application Under Test

**Application Name**  
LMS B2B Dibimbing Website

**Login URL**  
https://lms-b2b.do.dibimbing.id/dibimbingqa/login

**Covered Features**
- Training module
- Employee module

Only the Training and Employee features are included in this automation project.

---

## Tech Stack

- Java
- Selenium WebDriver
- Maven
- Page Object Model (POM)
- GitHub Actions (CI/CD)

---

## Project Structure

src
└── test
└── java
├── pages # Page Object classes
├── tests # Test scenarios
└── utils # WebDriver and helper utilities


---

## How to Run the Tests

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/api-automation-graphql.git
2. Navigate to the project directory
   cd api-automation-graphql
3. Execute tests using Maven
   mvn clean test


