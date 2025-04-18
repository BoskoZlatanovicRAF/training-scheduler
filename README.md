# Training scheduler

This project is a comprehensive management system for fitness centers, built as a microservices architecture with a Vue.js frontend and Spring Boot backend. It enables fitness centers to manage workout schedules, user registrations, and trainer assignments while allowing users to book sessions and receive notifications.

## Key Features

- **User Management**: Registration and authentication for different user types (standard users, managers, admins)
- **Training Schedule Management**: Managers can create, schedule, and manage training sessions
- **Booking System**: Users can view and book available training sessions
- **Notifications**: Email notifications for session bookings, cancellations, and reminders
- **User Dashboard**: Personalized user interface showing booked trainings and account information

## Technology Stack

### Frontend
- **Vue.js 3**: Progressive JavaScript framework for building user interfaces
- **Vuex**: State management for Vue.js
- **Vue Router**: Official router for Vue.js
- **Bootstrap Vue**: UI components library
- **Syncfusion Scheduler**: For calendar and scheduling functionality
- **Axios**: HTTP client for API requests

### Backend Microservices

#### User Service
- **Spring Boot**: Java-based framework for microservices
- **Spring Data JPA**: Data access layer
- **PostgreSQL**: Relational database
- **JWT**: JSON Web Tokens for authentication and authorization

#### Training Service
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **REST APIs**: For communication between services

#### Notification Service
- **Spring Boot**
- **Spring Mail**: For email notifications
- **ActiveMQ**: Message broker for asynchronous communication between services
- **PostgreSQL**: For storing notification history

### Communication
- **RESTful APIs**: For client-server and inter-service communication
- **JMS (ActiveMQ)**: For asynchronous messaging between services

### Development Tools
- **Maven**: Dependency management and build tool
- **Git**: Version control

## Architecture

The application follows a microservices architecture with:
1. A Vue.js frontend providing the user interface
2. Three backend services:
   - User service for authentication and user management
   - Training service for managing training sessions
   - Notification service for handling email communications

The services communicate via REST APIs and message queues, ensuring loose coupling and maintainability.

## Getting Started

To run this project locally:

1. Clone the repository
2. Set up PostgreSQL database
3. Configure ActiveMQ
4. Run the backend services using Maven
5. Install frontend dependencies with npm
6. Start the frontend development server with `npm run serve`

Refer to individual service READMEs for detailed setup instructions.
