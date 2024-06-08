# Vacation Management System

Vacation Management System is a Spring Boot application designed to manage vacation requests within an organization. The system supports two roles: Employee and Admin. Employees can create leave requests, and Admins have the authority to approve or reject these requests.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributors](#contributors)

## Features

- **Employee Role:**
  - Create leave requests.
  - View their leave history.

- **Admin Role:**
  - View all leave requests.
  - Approve or reject leave requests.
  - Filter leave requests by employee, state, or year.
  - Update the status of leave requests.

## Technologies

- **Backend:**
  - Spring Boot
  - JPA/Hibernate
  - PostgreSQL
  - Liquibase (for database migrations)
  
- **Frontend:**
  - HTML
  - CSS
  - JavaScript
  
- **Containerization:**
  - Docker
  - Docker Compose

## Setup

To set up the project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/vacation-management-system-springboot.git
   cd vacation-management-system-springboot
   
2. Copy the example environment file and configure it:

```
cp .env.example .env
echo "API_URL=$(gp url 8080)" >> .env
```

3. Build and run the Docker containers:

```
docker-compose --profile dev build
docker-compose --profile dev up -d
```

4. Access the application:
   - The application will be accessible at the URL specified in the .env file.

5. Usage

Employees:
Log in to the system.
Navigate to the "Create Leave Request" section.
Fill out the leave request form and submit.

Admins:
Log in to the system.
Navigate to the "Manage Leave Requests" section.
View, filter, and update the status of leave requests.

Contributors
This project was created by:

Anis Ghabi
Sirine Trabelsi
Marwa Fakhfak
