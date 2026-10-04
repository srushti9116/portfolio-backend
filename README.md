# Portfolio Backend

A Java Spring Boot REST API backend for a personal portfolio and custom CMS.

The backend provides authentication, content management APIs, PostgreSQL persistence, media uploads, and contact-form email functionality.

## Technologies

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Maven
- Jakarta Validation
- Gmail SMTP

## Features

- Admin authentication using JWT
- About section management
- Skills CRUD
- Projects CRUD
- Blogs CRUD
- Experience CRUD
- Testimonials CRUD
- Services CRUD
- Media upload and management
- Contact form submission
- Contact messages stored in PostgreSQL
- Email notifications for contact submissions
- Protected admin operations
- Public read APIs for portfolio content

## API Endpoints

### Authentication

```text
POST /auth/login
POST /auth/refresh
