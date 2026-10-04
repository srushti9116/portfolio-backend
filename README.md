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

GET /about
PUT /about

GET /skills
POST /skills
PUT /skills/{id}
DELETE /skills/{id}

GET /projects
POST /projects
PUT /projects/{id}
DELETE /projects/{id}

GET /blogs
POST /blogs
PUT /blogs/{id}
DELETE /blogs/{id}

GET /experience
POST /experience
PUT /experience/{id}
DELETE /experience/{id}

GET /testimonials
POST /testimonials
PUT /testimonials/{id}
DELETE /testimonials/{id}

GET /services
POST /services
PUT /services/{id}
DELETE /services/{id}

POST /upload/image
GET /upload/media
DELETE /upload/media/{id}

POST /contact
GET /contact
PUT /contact/{id}/read
DELETE /contact/{id}

portfolio-backend
│
├── src/main/java/com/portfolio/backend
│   ├── config
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── security
│   └── service
│
├── src/main/resources
│   ├── application-example.properties
│   └── application.properties   (local only)
│
├── src/test
├── pom.xml
├── mvnw
└── mvnw.cmd
