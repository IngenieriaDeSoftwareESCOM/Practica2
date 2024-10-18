# User Management System

![Version](https://img.shields.io/badge/version-v1.0.0-brightgreen)
![Java](https://img.shields.io/badge/java-21-blue)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.3.4-green)
![MariaDB](https://img.shields.io/badge/mariadb-10.5.9-orange)

## Overview

The User Management System provides a single login view with functionalities for user creation, role assignment, and user restrictions based on roles. This system is designed to manage users effectively while ensuring security through role-based access control.

## Features

- **User Registration**: Create new users.
- **Role Assignment**: Assign roles to users.
- **Role-Based Access Control**: Restrict access to functionalities based on user roles.

## Technologies Used

- Java 21
- Spring Boot 3.3.4
- MariaDB 10.5.9
- Maven for dependency management

## Database Configuration

Make sure to configure your `application.properties` file with the correct database connection settings for MariaDB:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## API Endpoints

### User Management

- **Register User**
  - **Endpoint**: `POST /api/Create/User`
  - **Request Body**: `{"email": "user@example.com", "password": "userpassword"}`
  - **Response**: `200 OK` with user details if successful.

- **Get User by ID**
  - **Endpoint**: `GET /api/Get/User/{id}`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` with user details or `404 Not Found` if user doesn't exist.

- **Get All Users**
  - **Endpoint**: `GET /api/Get/Users`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` with a list of users.

- **Update User**
  - **Endpoint**: `PUT /api/Update/User`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Request Body**: `{"id": 1, "email": "new_email@example.com", "password": "newpassword"}`
  - **Response**: `200 OK` with updated user details.

### Role Management

- **Register Role**
  - **Endpoint**: `POST /api/Create/Role`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Request Body**: `{"name": "ROLE_ADMIN"}`
  - **Response**: `200 OK` with role details if successful.

- **Get All Roles**
  - **Endpoint**: `GET /api/Get/Roles`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` with a list of roles.

- **Delete Role**
  - **Endpoint**: `DELETE /api/Delete/Role/{id}`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `204 No Content` if deletion is successful.

### User Role Management

- **Assign User to Role**
  - **Endpoint**: `POST /api/Create/User/Role`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Request Body**: `{"user": {"id": 1}, "role": {"id": 1}}`
  - **Response**: `200 OK` with user-role details if successful.

- **Remove User from Role**
  - **Endpoint**: `DELETE /api/Remove/User/Role/{id}`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` if removal is successful.

- **Get User Roles**
  - **Endpoint**: `GET /api/Get/User/{id}/Roles`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` with a list of roles assigned to the user.

- **Get All User Roles**
  - **Endpoint**: `GET /api/Get/Users/Roles`
  - **Request Header**: `Authorization: Bearer {token}`
  - **Response**: `200 OK` with a list of all user roles.

## Running the Application

To run the application, follow these steps:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/IngenieriaDeSoftwareESCOM/Practica2
   cd your-repo-directory
   ```

2. **Build the Application**:

   ```bash
   mvn clean install
   ```

3. **Run the Application**:

   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**:
   - The application will be available at `http://localhost:8080`.

## Notes

- Ensure that MariaDB server is installed and running.
- Create the necessary database and configure the `application.properties` file accordingly.