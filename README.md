# My Java Bethen Application

This Java application provides a robust and scalable backend solution, leveraging modern technologies to ensure security, efficiency, and maintainability. 
It implements Role-Based Access Control (RBAC) to manage user permissions, JSON Web Tokens (JWT) for secure authentication, Model Mapper for efficient data transfer object (DTO) mapping, MongoDB for flexible data storage, and Lombok to reduce boilerplate code.


It typically allows users create account, fund there account, fix money to get a return in 90days
It leverages on Budpay infrastructure to terminate the payment process.


## Features

* **Role-Based Access Control (RBAC):**
    * Fine-grained control over user permissions.
    * Defines roles and assigns permissions to each role.
    * Dynamically manages user roles and permissions.
* **JSON Web Token (JWT) Authentication:**
    * Secure and stateless authentication.
    * JWTs are used to verify user identity.
    * Token-based authorization for protected resources.
* **MongoDB Integration:**
    * NoSQL database for flexible and scalable data storage.
    * Utilizes Spring Data MongoDB for seamless integration.
    * Supports complex data structures and queries.
* **Model Mapper:**
    * Automates the mapping between DTOs and entity objects.
    * Reduces boilerplate code and improves code readability.
    * Simplifies data transformation.
* **Lombok:**
    * Reduces boilerplate code by automatically generating getters, setters, constructors, and other methods.
    * Improves code conciseness and maintainability.
* **Spring Boot:**
    * Creates stand-alone, production-grade Spring based Applications that you can "just run".
    * Embedded Tomcat, Jetty or Undertow directly.
* **Exception Handling:**
    * Centralized exception handling for clean error responses.
    * Custom exception classes for specific error scenarios.

## Technologies Used

* **Java:** Programming language.
* **Spring Boot:** Framework for building Java applications.
* **Spring Data MongoDB:** MongoDB integration with Spring.
* **Spring Security:** Security framework for authentication and authorization.
* **JSON Web Token (JWT):** Authentication and authorization standard.
* **Model Mapper:** Object mapping library.
* **Lombok:** Code generation library.
* **MongoDB:** NoSQL database.
* **Maven/Gradle:** Build tool.

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 21 or higher
* MongoDB installed and running
* Maven 



5. Configure your MongoDB connection string in the application.properties or application.yml file.

## Some API Endpoints

* `/api/v1/members/createNewMember`: Register a new user.
* `/api/v1/members/getAllMembers`: Retrieve all registered members (requires ADMIN role)
* `/api/v1/transactions/paymentLink`: Generate payment link from Budpay (requires BASIC role).
* `/api/v1/transactions/webhook`: Receives webhook from payment processor.
* `/api/v1/transactions/activate`: Activates user plan for a minimum of 90days.
* `/api/v1/members/getMemberById/{id}`: Fetches specific member with a specified ID.




