# Loan Management System

A **Spring Boot** application for managing loan applications, approvals, repayments, and admin controls.

## 🚀 Features
- User registration & authentication (Spring Security & JWT)
- Apply for loans, track repayments
- Admin: Approve/reject loans, update interest rates & tenure
- AOP Logging for transactions
- Redis caching for frequently accessed data

## 🛠 Tech Stack
### **Backend**
- **Spring Boot**: Framework for building the application.
- **Spring Security**: Provides authentication & role-based access control.
- **JWT (JSON Web Token)**: Used for secure token-based authentication.

### **Database**
- **MySQL**: Relational database used to store user, loan, and repayment details.
- **Spring Data JPA**: ORM layer for database interactions.

### **Caching**
- **Redis**: Used for caching frequently accessed data, such as interest rates, to improve performance.

### **Authentication & Security**
- **Spring Security with JWT**:
  - Secure authentication without session management.
  - Users receive a JWT token upon login, which is required for API access.
  - Role-based access control (Customer & Admin).
  - Token validation for each request.

### **Logging & Monitoring**
- **Spring AOP (Aspect-Oriented Programming)**:
  - Logs important actions like loan approvals, repayments, and transactions.
  - Improves maintainability and debugging.


