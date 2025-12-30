# Ecommerce Backend API

A Spring Boot REST API for an ecommerce application with JWT authentication, product management, and order processing.

## Features

- **User Authentication**: JWT-based authentication with registration and login
- **Product Management**: CRUD operations for products with category support
- **Order Management**: Create and track customer orders
- **Role-based Access**: Admin and User roles with different permissions
- **Database Integration**: H2 in-memory database with JPA/Hibernate

## Tech Stack

- Spring Boot 3.4.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Setup Instructions

### Prerequisites
- Java 17
- Maven

### Running the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

**H2 Console**: Available at `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/signin` - User login

### Products (Public)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/search?name={name}` - Search products

### Products (Admin only)
- `POST /api/products` - Create product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Categories (Public)
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID

### Categories (Admin only)
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Orders (Authenticated)
- `GET /api/orders` - Get user's orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order

### Orders (Admin only)
- `GET /api/orders/all` - Get all orders
- `PUT /api/orders/{id}` - Update order status

## Sample Data

### Register User
```json
POST /api/auth/signup
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Create Category
```json
POST /api/categories
{
  "name": "Electronics",
  "description": "Electronic devices and gadgets"
}
```

### Create Product
```json
POST /api/products
{
  "name": "Smartphone",
  "description": "Latest smartphone with advanced features",
  "price": 699.99,
  "stockQuantity": 50,
  "imageUrl": "https://example.com/image.jpg",
  "category": {
    "id": 1
  }
}
```

## Authentication

Include JWT token in Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Default Admin User

To create an admin user, register normally and then manually update the role in the H2 console:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
```