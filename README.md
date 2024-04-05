# ChâTop API

ChâTop API is a Spring Boot application. It provides registration, authentication, access to rental listings, and messaging functionnalities.

This project was generated with [Spring Boot](https://spring.io/projects/spring-boot) version 3.2.4.

## Dependencies

- `Lombok`: Optimize classes
- `Spring Data JPA`: Manage data persistence with the database
- `Spring Security`: Layer of security
- `OAuth2 Resource Server`: Token authentication
- `Spring Web`: Implementing Endpoint
- `Model Mapper`: Create DTO
- `Springdoc`: Swagger documentation

## Prerequisites

This application use MySQL like database so be sure you have it, else download it (https://dev.mysql.com/downloads/installer/).

If you are not familiar with this, I recommend you install MySQL Workbench (https://dev.mysql.com/downloads/workbench/). I will explain how to launch this project with this software.

## Installation

### 1. Clone the repository
```
git clone https://github.com/MarieM512/FS_OC_P3.git
```

### 2. Create a new databse in MySQL

You can import the script `chatop.sql`

If you don't know how to do it, please follow the instructions

- Open MySQL Workbench
- Connect to your MySQL server (port:3306 for exemple)
- Click on the fourth icon from the left
- In Schema Name enter a name for your database, be sure to retain it because we will need it after.
- Click on Apply
- Click on your new database at the left
- Go to the "File" > "Open SQL Script" > Select `chatop.sql` from the repository
- Click on the execute icon

### 3. Update the application with your config

> [!IMPORTANT]
> For jwtKey, be sure that this variable contain 32 bytes

You have two options:

1. Add `.env` file to the repository root and copy this (recommended)
```yaml
# Server port
SERVER_PORT = #3001

# Bearer Token Config
JWT_KEY = #azertyuiopmlkjhgfdsqwxcvbnazerty

# Database
DATABASE_URL = #jdbc:mysql://localhost:3306/oc_chatop
DATABASE_USERNAME = #username
DATABASE_PASSWORD = #password
```

2. Update `application.properties` and `src/main/java/com/mariemetay/configuration/SpringSecurityConfig.java` with your config (not recommended)
```yaml
server.port=${SERVER_PORT} #3001

#Database
spring.datasource.url=${DATABASE_URL} #jdbc:mysql://localhost:3306/oc_chatop #replace oc_chatop with the name of your database
spring.datasource.username=${DATABASE_USERNAME} #username
spring.datasource.password=${DATABASE_PASSWORD} #password
```

```java
@Value("${JWT_KEY}") // delete this
private String jwtKey; // private String jwtKey = "mySecretKey";
```

### 4. Run the application

If you have Maven on your pc, do this:
```
mvn clean install
```

```
mvn spring-boot:run
```

Else:

- In Visual Studio Code install `Extension Pack for Java` extension
- Go to `src/main/java/com/mariemetay/ChatopapiApplication.java`
- Click on Run, juste above the main function

## Swagger Documentation

> [!IMPORTANT]
> Be sure to replace 3001 with your server port

You can access to the API documentation : http://localhost:3001/api-docs/swagger-ui/index.html#/


## Compatibilty

You can try the API with this web application https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring

## Information

Spring Security and JWT are used for secure communication.

The application is developed with a layered architecture
- Configuration
- Controller
- Model
- Repository
- Service


