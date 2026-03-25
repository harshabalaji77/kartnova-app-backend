<div align="center">

# Kartnova - Backend API

</div>

<div align="center">

![Kartnova Logo](https://img.shields.io/badge/Kartnova-E--Commerce-blue?style=for-the-badge)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen?style=flat-square&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=flat-square&logo=apache-maven)

[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=flat-square)](https://github.com/yourusername/kartnova/actions)

</div>

## 📋 Overview

**Kartnova** is a comprehensive e-commerce backend API built with Spring Boot that provides a complete shopping platform solution. It handles product management, user authentication, shopping cart functionality, order processing, and payment integration with Razorpay.

## 🚀 Features

### Core Functionality
- **Product Management**: Browse, search, and filter products by category
- **User Authentication**: Secure JWT-based authentication and authorization
- **Shopping Cart**: Add/remove items, update quantities
- **Order Management**: Complete order lifecycle from placement to delivery
- **Payment Integration**: Razorpay payment gateway integration
- **Admin Dashboard**: Administrative controls for products, users, and orders

### Technical Features
- **RESTful API**: Clean and well-documented REST endpoints
- **Security**: JWT token-based authentication with Spring Security
- **Database Integration**: JPA/Hibernate with MySQL
- **Cross-Origin Support**: CORS configuration for frontend integration
- **Real-time Updates**: Spring Boot DevTools for development

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Boot** | 4.0.2 | Main application framework |
| **Java** | 17 | Programming language |
| **Spring Data JPA** | - | Database ORM |
| **Spring Security** | - | Authentication & Authorization |
| **MySQL** | 8.0+ | Database |
| **JWT** | 0.11.5 | Token-based authentication |
| **Razorpay** | 1.4.8 | Payment gateway |
| **Maven** | 3.6+ | Build tool |

## 📁 Project Structure

```
Kartnova/
├── src/main/java/com/kodnest/app/
│   ├── entities/                 # JPA Entities
│   │   ├── Product.java
│   │   ├── User.java
│   │   ├── Order.java
│   │   ├── CartItem.java
│   │   └── ...
│   ├── controllers/              # REST Controllers
│   │   ├── usercontrollers/      # User-facing APIs
│   │   └── admincontrollers/     # Admin APIs
│   ├── services/                 # Service interfaces
│   ├── serviceimplementations/   # Service implementations
│   ├── repositories/             # JPA repositories
│   └── filters/                  # JWT Authentication filter
├── src/main/resources/
│   └── application.properties     # Configuration
└── pom.xml                       # Maven dependencies
```

## 🚀 Quick Start

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/kartnova.git
   cd kartnova/Kartnova
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE kartnova;
   -- Update credentials in application.properties
   ```

3. **Configure Application**
   ```properties
   # Update src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/kartnova
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   # Update JWT secret for production
   jwt.secret=your-very-long-secure-secret-key
   
   # Update Razorpay credentials
   razorpay.key_id=your_razorpay_key_id
   razorpay.key_secret=your_razorpay_key_secret
   ```

4. **Build and Run**
   ```bash
   # Using Maven Wrapper
   ./mvnw clean install
   ./mvnw spring-boot:run
   
   # Or using Maven directly
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080`
   - API endpoints: `http://localhost:8080/api/*`

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | User registration |
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/logout` | User logout |

### Product Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products (with optional category filter) |
| GET | `/api/products/{id}` | Get product details |
| GET | `/api/products/search` | Search products |

### Cart Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart` | Get user's cart |
| POST | `/api/cart/add` | Add item to cart |
| PUT | `/api/cart/update` | Update cart item |
| DELETE | `/api/cart/remove/{id}` | Remove item from cart |

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get user's orders |
| POST | `/api/orders/place` | Place new order |
| GET | `/api/orders/{id}` | Get order details |

### Payment Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payment/create-order` | Create Razorpay order |
| POST | `/api/payment/verify` | Verify payment |

### Admin Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| POST | `/api/admin/products` | Add new product |
| PUT | `/api/admin/products/{id}` | Update product |
| DELETE | `/api/admin/products/{id}` | Delete product |

## 🔧 Configuration

### Environment Variables

Create a `.env` file in the root directory:

```env
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/kartnova
DB_USERNAME=root
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your-very-long-secure-secret-key-for-production
JWT_EXPIRATION=3600000

# Razorpay Configuration
RAZORPAY_KEY_ID=your_razorpay_key_id
RAZORPAY_KEY_SECRET=your_razorpay_key_secret

# Server Configuration
SERVER_PORT=8080
```

### Database Schema

The application uses the following database schema. You can create the tables manually using the SQL queries below or let Hibernate auto-generate them with `spring.jpa.hibernate.ddl-auto=update`.

#### Core Tables

```sql
-- Users table for authentication and user management
CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
);

-- JWT tokens for session management
CREATE TABLE jwt_tokens (
    token_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    PRIMARY KEY (token_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Product categories for organization
CREATE TABLE categories (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (category_id)
);
```

#### Product Management

```sql
-- Products catalog
CREATE TABLE products (
    product_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    category_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

-- Product images gallery
CREATE TABLE productimages (
    image_id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    image_url TEXT NOT NULL,
    PRIMARY KEY (image_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);
```

#### Shopping Cart & Orders

```sql
-- Shopping cart items
CREATE TABLE cart_items (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- Customer orders
CREATE TABLE orders (
    order_id VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order line items
CREATE TABLE order_items (
    id INT NOT NULL AUTO_INCREMENT,
    order_id VARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);
```

#### Entity Relationships

```
users (1) ←→ (N) jwt_tokens
users (1) ←→ (N) cart_items
users (1) ←→ (N) orders

categories (1) ←→ (N) products
products (1) ←→ (N) productimages
products (1) ←→ (N) cart_items
products (1) ←→ (N) order_items

orders (1) ←→ (N) order_items
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=ProductServiceTest

# Run with coverage
./mvnw test jacoco:report
```

### Test Coverage

- Unit tests for service layers
- Integration tests for controllers
- Repository layer testing
- Security testing

## 🔒 Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-based Access Control**: USER and ADMIN roles
- **CORS Configuration**: Secure cross-origin requests
- **Input Validation**: Request parameter validation

## 📦 Deployment

### Docker Deployment

```dockerfile
# Dockerfile
FROM openjdk:17-jdk-slim
COPY target/kartnova-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# Build and run
docker build -t kartnova .
docker run -p 8080:8080 kartnova
```

### Production Considerations

- Use environment variables for sensitive data
- Enable HTTPS in production
- Configure database connection pooling
- Set up proper logging
- Monitor application performance

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Java coding conventions
- Use meaningful variable names
- Add proper Javadoc comments
- Write unit tests for new features

## 📝 Changelog

### Version 0.0.1-SNAPSHOT
- Initial release
- Basic e-commerce functionality
- JWT authentication
- Razorpay integration
- Admin panel

## 🐛 Issues & Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Check MySQL service is running
   - Verify database credentials
   - Ensure database exists

2. **JWT Token Issues**
   - Check JWT secret configuration
   - Verify token expiration time
   - Ensure proper token format

3. **CORS Issues**
   - Verify frontend URL in CORS configuration
   - Check if credentials are allowed

### Reporting Issues

Please report issues on the [GitHub Issues](https://github.com/yourusername/kartnova/issues) page with:

- Detailed description of the issue
- Steps to reproduce
- Expected vs actual behavior
- Environment details



## 🙏 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) - The application framework
- [Razorpay](https://razorpay.com/) - Payment gateway integration
- [MySQL](https://www.mysql.com/) - Database solution
- [JWT](https://jwt.io/) - Authentication tokens

---

<div align="center">

**⭐ Star this repository if it helped you!**

Made with ❤️ by [Kartnova Team](https://github.com/yourusername)

</div>
