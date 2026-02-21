[![CodeFactor](https://www.codefactor.io/repository/github/dmitriy-iliyov/clicker-backend/badge)](https://www.codefactor.io/repository/github/dmitriy-iliyov/clicker-backend)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-336791?logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7+-DC382D?logo=redis)
![Lua](https://img.shields.io/badge/Lua-Redis%20Scripts-2C2D72?logo=lua)
![WebSocket](https://img.shields.io/badge/WebSocket-gray?logo=websocket)

## Overview

This repository contains the backend implementation for a real-time clicker game. It features a robust, scalable architecture designed to handle a high volume of concurrent users and clicks. The backend supports user account management, in-game currency, and a sophisticated click-processing system that ensures fairness and data integrity.

## Key Features

- **Real-Time Click Processing**: Utilizes WebSockets for instant communication between the client and server, providing a seamless and responsive user experience.
- **Atomic Click Operations**: Employs Redis and Lua scripts to ensure that every click is processed atomically, preventing race conditions and ensuring data consistency even under high load.
- **User and Wallet Management**: Comprehensive system for managing user accounts, authentication, and in-game currency wallets.
- **Modular Architecture**: Built using a modular monolithic approach, allowing for clear separation of concerns and easier maintenance.

## Architecture

The project is implemented as a modular monolithic system. This architecture allows for scalability and a clean separation of concerns, with each module handling a specific business domain.

### Modules

- **app**: The main application module. It launches the Spring Boot application and manages database migrations using Flyway.
- **core**: The heart of the application, containing the core business logic. This includes domain models (`User`, `Wallet`, `Currency`), services, repositories, and the primary security configurations.
- **clicker**: This module houses the game's core functionality. It includes the WebSocket handler for real-time communication and the logic for processing clicks using Redis and Lua.
- **libs/auth**: A reusable library that provides the fundamental authentication and authorization mechanisms, based on Spring Security and JWT. This library is utilized by the `core` and `clicker` modules to secure endpoints and WebSocket communication.
- **libs/utils**: A set of utility classes, including a UUID generator, used across the application.
- **libs/exceptions**: Defines custom, application-specific exceptions for standardized error handling.

## Non-functional requirements

### Authentication & Security

- **Stateless Authentication**: The system uses JWT for stateless authentication, with tokens transmitted securely to authorize both REST API requests and WebSocket sessions.
- **WebSocket Security**: WebSocket connections are secured using a custom filter that validates JWT tokens during the handshake process, ensuring that only authenticated users can establish a connection.
- **Role-Based Access Control (RBAC)**: Security is enforced at the API and service levels, with different roles and permissions for various user actions.

### Caching & Performance

- **Redis for High Performance**: Redis is used as a high-performance in-memory data store for caching frequently accessed data and for managing click processing.
- **Atomic Operations with Lua**: To handle high-frequency click events without data corruption, the system uses Lua scripts executed on the Redis server. This guarantees that each click is processed as a single, atomic operation.

## Run

The project is fully containerized using Docker for easy setup and deployment.

To build and run the project locally, execute the following commands:

```bash
docker compose build
docker compose up -d
```
