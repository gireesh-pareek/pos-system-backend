🛒 POS System Backend


A production-ready Spring Boot backend for a Point of Sale (POS) system designed to handle real-world retail operations including store management, inventory tracking, order processing, refunds, authentication, and shift reporting.

Built with clean architecture principles and scalable design.

📌 Features

✔ JWT-based Authentication & Role Authorization
✔ Store & Branch Management
✔ Product & Category Management
✔ Inventory Tracking
✔ Order & Order Items Processing
✔ Payment Summary Handling
✔ Refund Management
✔ Employee Management
✔ Shift Reporting
✔ Customer Management
✔ Clean Layered Architecture

🏗️ Tech Stack
Layer	Technology
Language	Java 21
Framework	Spring Boot
Security	Spring Security + JWT
ORM	Spring Data JPA (Hibernate)
Database	MySQL (Configurable)
Build Tool	Maven
Architecture	RESTful APIs

🧱 Architecture
This project follows a standard Layered Architecture Pattern:

Controller  →  Service  →  Repository  →  Database
                  ↓
                DTO + Mapper

Layers Explained
Controller Layer → Handles HTTP requests
Service Layer → Business logic
Repository Layer → Database interaction
Entity Layer → JPA Models
DTO Layer → Data transfer objects
Mapper Layer → Entity ↔ DTO conversion
Security Layer → JWT authentication & authorization
Exception Layer → Global error handling

📂 Project Structure
src/main/java/com/gireesh/
├── configuration/      # Security & JWT configuration
├── controller/         # REST Controllers
├── service/
│   └── impl/           # Business logic implementation
├── repository/         # JPA repositories
├── modal/              # Entity classes (Database tables)
├── payload/
│   ├── dto/
│   ├── request/
│   └── response/
├── mapper/             # Entity-DTO mappers
├── domain/             # Enums (Roles, Status, Payment types)
├── exception/          # Global exception handling
└── PosSystemApplication.java

🔐 Security
JWT Token generation & validation
Role-based access control
Custom UserDetailsService
Secure API endpoints
Stateless session management
Supported Roles
ADMIN
EMPLOYEE
MANAGER (if applicable)

🗄️ Core Modules

👤 Authentication
Register User
Login
JWT Token generation

🏬 Store & Branch
Create / Update Stores
Branch Management
Store status tracking

📦 Product & Category
Add / Update / Delete products
Category mapping
Inventory sync

📊 Inventory
Track stock levels
Auto update during order/refund

🧾 Orders
Create Orders
Order Items
Order Status tracking
Payment Summary

💸 Refund
Refund creation
Inventory adjustment

👥 Customer
Create & manage customers

👨‍💼 Employee
Employee management
Shift assignment
📈 Shift Report
Daily sales summary
Performance tracking

🧩 Database Entities
User
Store
Branch
Product
Category
Inventory
Customer
Order
OrderItem
Refund
ShiftReport
PaymentSummary
StoreContact

⚙️ Getting Started
1️⃣ Clone the Repository
git clone https://github.com/your-username/pos-system-backend.git
cd pos-system-backend

2️⃣ Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/pos_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

3️⃣ Run the Application

Using Maven:

mvn clean install
mvn spring-boot:run

Or using wrapper:

./mvnw spring-boot:run


Application runs on:

http://localhost:8080

🧪 API Testing
You can test APIs using:

Postman

Curl

Swagger (if integrated later)

Authentication Flow
Register user
Login → Receive JWT
Add header:
Authorization: Bearer <your-token>
Access secured endpoints

🛠 Design Principles
Separation of Concerns
Clean Code Practices
DTO Pattern
Mapper Pattern
Service Abstraction
Global Exception Handling
Role-Based Security
Scalable Modular Structure

🚀 Future Enhancements
Swagger API documentation
Unit & Integration Tests
Pagination & Sorting
Audit Logging
Docker Support
CI/CD Integration
Multi-Store Scalability Optimization

📈 Why This Project?
This project demonstrates:
Real-world backend system design
Production-level Spring Security implementation
Complex entity relationships
Financial transaction handling
Clean, scalable backend architecture

Ideal for:
Retail POS systems
Multi-branch store management
Backend architecture demonstrations
Portfolio projects

👨‍💻 Author

Gireesh Pareek
Backend Developer | Java | Spring Boot
Banking Domain Experience | Asset Management Systems
