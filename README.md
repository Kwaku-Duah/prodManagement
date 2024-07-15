# E-Commerce Product Management System

## Overview

This project is an E-Commerce Product Management System built using Spring Boot. It allows for advanced product management with features like profiles, external configuration, and actuators. The system uses a binary tree for efficient product categorization and retrieval.

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Boot Actuator
- Thymeleaf
- Hibernate
- PostgreSQL
- Jakarta Persistence API (JPA)
- Binary Tree Data Structure

## Setup and Configuration

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL

### Environment Configuration

Create the following properties files for different environments:

**application.properties**

```properties
spring.application.name=demo-app
server.port=8084
server.error.include-message=always
server.error.include-binding-errors=always
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=duaSHKH!229
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format-sql=true
spring.jpa.show-sql=true
spring.session.jdbc.initialize-schema=always
spring.session.jdbc.table-name=SPRING_SESSION
spring.session.store-type=jdbc
```

**application-dev.properties**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_dev
spring.datasource.username=postgres
spring.datasource.password=devPassword
```

**application-prod.properties**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_prod
spring.datasource.username=postgres
spring.datasource.password=prodPassword
```

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/git@github.com:Kwaku-Duah/ecommerce-product-management.git
   cd ecommerce-product-management
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Access the application at `http://localhost:8084`.

## Key Features

- Product and Category Management (Add, Edit, Delete, View)
- Efficient search functionality using binary trees
- Pagination and Sorting
- Custom Queries using JPQL and native SQL
- Profiles and external configuration
- Monitoring with Spring Boot Actuator
- Global Exception Handling with `@ControllerAdvice`
- Interceptors for Logging and Authentication

## API Endpoints

### Products

- GET `/products` - List all products with pagination and sorting
- GET `/products/search` - Search products by keyword
- GET `/products/add` - Show form to add a new product
- POST `/products` - Add a new product
- GET `/products/edit/{id}` - Show form to edit a product
- POST `/products/edit/{id}` - Edit a product
- GET `/products/delete/{id}` - Delete a product

### Categories

- GET `/categories` - List all categories
- GET `/categories/{id}` - View products by category
- GET `/categories/add` - Show form to add a new category
- POST `/categories` - Add a new category
- GET `/categories/edit/{id}` - Show form to edit a category
- POST `/categories/edit/{id}` - Edit a category
- GET `/categories/delete/{id}` - Delete a category

## Project Structure

- **Controllers:** Handle HTTP requests and responses
- **Services:** Contain business logic
- **Repositories:** Interface with the database
- **Models:** Define the structure of data
- **Utils:** Utility classes (e.g., Binary Tree)
