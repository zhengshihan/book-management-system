# Library Management System

## Project Overview

This project is a **Library Management System** built using **Spring Boot** and **Thymeleaf**. It provides basic CRUD functionalities for managing books. The system is ideal for managing a library or a small bookstore, including tracking book inventory, and user information.

## Features

- **Book Management**
  - Add, update, delete, and view book details
  - Search for books by title, author
- **User Management**
  - Add, update, delete, and view user information

## Technology Stack

- **Backend**: Spring Boot, Spring Data JPA, MySQL
- **Frontend**: Thymeleaf, HTML, CSS, Bootstrap
- **Database**: MySQL
- **Build Tool**: Maven

## Prerequisites

Before running the project, make sure you have the following installed:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or above
- [Maven 3.6+](https://maven.apache.org/install.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- An IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/)


# Library Management System

## How to Set Up and Run the Library Management System

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

### Step 2: Configure the Database

1. Install and configure **MySQL** on your machine.
2. Create a database named `library_db`:

    ```sql
    CREATE DATABASE library_db;
    ```

3. Update the `src/main/resources/application.properties` file with your MySQL credentials:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/library_db
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update
    ```

### Step 3: Build the Project

Run the following command to build the project:

```bash
mvn clean install
```

### Step 4: Run the Application

After building the project, use the following command to run the application:

```bash
mvn spring-boot:run
```

### Step 5: Access the Application

Once the application is running, open your web browser and go to:

```
http://localhost:8080
```
