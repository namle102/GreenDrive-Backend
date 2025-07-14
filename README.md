# 🍃🚗 GreenDrive Backend

This is the Spring Boot backend system for the **GreenDrive** electric vehicle store project. It includes APIs for:

* User registration and login (with JWT security)
* Vehicle management (CRUD, sorting, filtering, hot deals)
* Order placement and tracking
* Admin reports (event logs, usage summary, sales report)

---

## 🛠️ Technologies

* Java 21
* Spring Boot 3.5.2
* Spring Security + JWT
* MySQL
* Maven
* JPA + Hibernate
* Lombok
* ModelMapper

---

## ⚙️ Setup Instructions

### 🧬 1. Clone the Repository

```bash
git clone https://github.com/namle102/GreenDrive-Backend.git
cd greendrive-backend
```

### 📝 2. Update application.properties

Change the `username` and `password` to match your MySQL server:

```properties
spring.application.name=greendrive-backend

# Database connection
spring.datasource.url=jdbc:mysql://localhost:3306/greendrive_db
spring.datasource.username=root
spring.datasource.password=123321
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT Configuration
jwt.secret=thisIsMysecregtfrdesww233hoaiklasdleasdnamdgkjjhhtdhttebd54ns8877465sbbdd
jwt.expiration=3600000
```

### ▶️ 3. How to Run

You can run the app via Maven:

```bash
mvn spring-boot:run
```

Or use IntelliJ:

```bash
- Open the project
- Click Run
```

---

## 🔌 API Endpoints Summary

### 🔐 1. Authentication

| Method | Endpoint           | Description             |
| ------ | ------------------ | ----------------------- |
| POST   | `/api/auth/signup` | Register a new user     |
| POST   | `/api/auth/signin` | Login and receive token |

### 👥 2. Users

| Method | Endpoint           | Description           |
| ------ | ------------------ | --------------------- |
| GET    | `/api/admin/users` | Get all users (admin) |
| GET    | `/api/users/{id}`  | Get user by ID        |
| POST   | `/api/users`       | Create new user       |
| PUT    | `/api/users/{id}`  | Update user           |
| DELETE | `/api/users/{id}`  | Delete user           |

### 🚗 3. Vehicles

| Method | Endpoint               | Description         |
| ------ | ---------------------- | ------------------- |
| GET    | `/api/vehicles`        | Get paginated list  |
| GET    | `/api/vehicles/{id}`   | View vehicle detail |
| GET    | `/api/vehicles/filter` | Filter vehicles     |
| GET    | `/api/vehicles/deals`  | Hot deals           |
| POST   | `/api/vehicles`        | Add vehicle         |
| PUT    | `/api/vehicles/{id}`   | Update vehicle      |
| DELETE | `/api/vehicles/{id}`   | Delete vehicle      |

### 📦 4. Orders

| Method | Endpoint            | Description                   |
| ------ | ------------------- | ----------------------------- |
| POST   | `/api/orders/place` | Place an order                |
| GET    | `/api/orders/track` | Track by email & order number |

### 📊 5. Admin Reports

| Method | Endpoint               | Description               |
| ------ | ---------------------- | ------------------------- |
| GET    | `/api/admin/event-log` | View all logged events    |
| GET    | `/api/admin/usage`     | Vehicle views & purchases |
| GET    | `/api/admin/sales`     | Sales report by vehicle   |

---

## 🔐 Security

This backend uses JWT for authentication. Admin-only routes are secured using:

```java
.requestMatchers("/api/admin/**").hasRole("ADMIN")
```

To test, use a valid JWT token in the Authorization header:

```http
Authorization: Bearer <your_token>
```

---

## 🧪 Data Initializer

To help you test the backend easily, the app includes a `DataInitializer` class that runs on startup and:

* Creates sample users (1 admin and 9 regular users)
* Populates the database with sample vehicles
* Randomly assigns reviews to all vehicles

You can modify or disable this by editing the `DataInitializer` class.

---

## 🧷 Testing

All APIs were tested using Postman.

### 🔍 1. To Test Endpoints

* Make sure your MySQL server is running
* Ensure `application.properties` is configured properly
* Start the app using `mvn spring-boot:run`

### 🛡️ 2. Testing Admin Endpoints

To test `/api/admin/**` endpoints:

* Login via `/api/auth/signin` using the default admin credentials
* Copy the JWT token from the login response
* Add this token in your Postman request header:

```http
Authorization: Bearer <your_token>
```

If your token is valid and has role `ADMIN`, you can access:

* `/api/admin/sales`
* `/api/admin/event-log`
* `/api/admin/usage`