# Explore Municipalities

A platform to discover, document, and promote points of interest across municipalities. The app enables local administrations and community contributors to create, manage, and share structured place information and multimedia content. It focuses on the digitalization and enhancement of local heritage and services.

Contributors:

- Matteo Cardellini
- Leonardo Pigliacampo

## Overview

Explore Municipalities helps municipalities showcase local attractions and services through crowdsourced points of interest. The platform combines an interactive map-based frontend with a RESTful backend to deliver a responsive, secure, and extensible experience for administrators, contributors and third-party integrators.

The system is collaborative by design: verified community contributors can submit new points of interest and multimedia content. Submissions are reviewed and approved by municipal managers or designated administrators before going live.

## Key features

- Create and manage points of interest with descriptions, images and location data
- Role-based access for municipal managers and verified contributors
- Interactive maps and geolocation features for discovery and navigation
- REST API for integrations and third-party clients

## Frontend

![image](https://github.com/user-attachments/assets/7d91401a-e5b4-47f1-9339-6c87f3c5a02a)
![image](https://github.com/user-attachments/assets/5348101b-fe08-4bc8-9b3f-35d43e633c93)

The frontend is implemented with Angular as a Single Page Application (SPA) and is responsive for desktop and mobile devices. Main frontend libraries:

- auth0/angular-jwt — JWT handling for Angular
- ng-bootstrap — Bootstrap components for Angular
- leaflet — Interactive maps, used with OpenStreetMap (OSM) tiles

## Backend

The backend is built with Spring Boot and exposes REST APIs for the platform. Data persistence is handled via Spring Data JPA and a relational database (SQL Server in the current setup). Notable dependencies include:

- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-data-jpa
- jjwt (JWT support)

## Security

Security is a priority. Highlights:

- Authentication & Authorization: implemented with Spring Security and JWT-based tokens.
- Passwords: stored using bcrypt hashing.
- SQL safety: JPA/ORM reduces direct SQL usage and helps mitigate injection risks when used correctly.
- CORS & development proxy: the frontend includes a proxy configuration (`proxy.conf.json`) to simplify local development. For production, configure CORS and reverse proxy rules at the server or gateway level.

## API documentation

Swagger UI is available when the backend runs locally at:

http://localhost:8080/swagger-ui/index.html

## Getting started (development)

- Start the backend (Spring Boot) on port 8080.
- Serve the Angular frontend (it uses `proxy.conf.json` to forward API requests to the backend during development).

Refer to the project folders `backend/` and `frontend/` for build and run instructions.
