# HMB Backend

This is the backend component of the HMB project, developed using Spring Boot and PostgreSQL. The backend provides RESTful APIs for user authentication, leave applications, user queries, and more. This project was completed as part of an internship at HMB/Bilgi Teknolojileri Genel Müdürlüğü over a span of 20 days.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Usage](#usage)
7. [Authors](#authors)
8. [Contributing](#contributing)
9. [License](#license)

## Project Overview

The HMB backend is a Spring Boot application that provides various functionalities including user authentication, user management, leave application processing, and search capabilities. It interacts with a PostgreSQL database and exposes RESTful APIs for the frontend to consume.

## Features

- **User Authentication**: Allows users to sign in and authenticate.
- **User Management**: Supports creating, updating, and querying user information.
- **Leave Application**: Users can apply for leave, and executives can approve or decline requests.
- **Search Functionality**: Allows searching for users based on TCKN, name, and surname.
- **Admin Panel**: Accessible to administrators for managing users and contact messages.
- **Profile Management**: Users can update their profile and change passwords.

## Tech Stack

- **Spring Boot**: Framework for building the backend application.
- **PostgreSQL**: Database for storing user and leave application data.
- **Java**: Programming language used for backend development.
- **Spring Security**: For handling authentication and authorization.
- **Maven**: Dependency management and build tool.

## Installation

### Prerequisites

- Java 17 or later.
- PostgreSQL database.
- Maven.

### Steps

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/zgeblbl/hmb-backend.git
   ```

2. Navigate to the Project Directory:
  ```bash
   cd hmb-backend
   ```

3. Configure the Database:

- Create a PostgreSQL database.
- Update the application.properties file in src/main/resources with your database credentials.

4. Build the Project:
 ```bash
   mvn clean install
   ```
   
5. Run the Application
 ```bash
   mvn spring-boot:run
   ```
## Configuration
Update the src/main/resources/application.properties file with your PostgreSQL database credentials and other configuration details:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
## Usage
After running the application, you can access the API endpoints at http://localhost:9090.

### Example API Requests
- User Query:
  ```
   POST http://localhost:9090/api/hmb/users/search
   ```
Request body:
```
{
    "TCKN": "123",
    "firstName": "john",
    "lastName": "doe"
}
```

- Add User:
  ```
   POST http://localhost:9090/api/hmb/users/addUser
   ```
Request body:
```
{
    "firstName": "John",
    "lastName": "Doe",
    "tckn": "12345678901",
    "isUserDeleted": false,
    "email": "jd@gmail.com",
    "password": "12345",
    "department": {
        "departmentId": 1
    },
    "title": {
        "titleId": 1
    }
}
```
## Authors
- Özge Bülbül
- Eda Eylül Aslan

## Contributing
If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (git checkout -b feature/YourFeature).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature/YourFeature).
5. Create a new Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.



   
