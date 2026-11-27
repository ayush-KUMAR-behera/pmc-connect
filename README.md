# PMC Connect

PMC Connect is a full-stack coaching centre management system designed to manage teachers, coaching centres, and daily class reports across multiple centres.

## Project Objectives

- Manage coaching centres and teacher accounts.
- Allow teachers to register and wait for admin approval.
- Enable approved teachers to submit daily class reports.
- Track student attendance and topics covered in each class.
- Provide role-based access control for Admins and Teachers.

## Tech Stack

**Backend:** Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate

**Database:** MySQL

**Frontend:** React, Vite, Tailwind CSS *(planned)*

**Tools:** Eclipse, VS Code, Git, GitHub, Postman

## User Roles

- **Admin:** Manage centres, approve teachers, and assign teachers to centres.
- **Teacher:** Submit class reports after approval and centre assignment.

## Current Progress

- Database design and entity modelling
- Repository, DTO, service, and controller layers
- Class report creation API
- Input validation and exception handling
- Spring Security with HTTP Basic Authentication
- BCrypt password encryption
- Role-based authorization foundation

## Project Structure

```text
pmc-connect/
├── backend/    # Spring Boot application
├── docs/       # Project documentation
├── README.md   # Project overview
└── .gitignore
```

## Project Goal

Build a secure, maintainable, and practical coaching centre management application using industry-standard full-stack development practices.