# Car Rental Application

A car rental application built with **Domain-Driven Design (DDD)** and **CQRS (Command Query Responsibility Segregation)** architecture using **Java Spring Boot**.

## Architecture Overview

This application follows a clean architecture approach with clear separation of concerns:

- **Domain Layer**: Core business logic, entities, value objects, aggregates, and domain services
- **Application Layer**: Use cases, commands, queries, handlers, and DTOs
- **Infrastructure Layer**: Repository implementations, database configuration
- **Presentation Layer**: REST API controllers

## Architecture Diagram

See [DDD_ARCHITECTURE_DIAGRAM.md](DDD_ARCHITECTURE_DIAGRAM.md) for detailed architecture diagrams.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (for development) / **PostgreSQL** (for production)
- **Lombok** (for reducing boilerplate code)
- **MapStruct** (for DTO mapping)
- **Maven** (build tool)

## Project Structure

```
src/main/java/com/carrent/
├── domain/                          # Domain Layer
│   ├── model/
│   │   ├── aggregate/              # Aggregates (Car, Customer, Rental)
│   │   ├── valueobject/            # Value Objects (Money, RentalPeriod, etc.)
│   │   └── event/                  # Domain Events
│   ├── repository/                 # Repository Interfaces
│   └── service/                    # Domain Services
├── application/                     # Application Layer
│   ├── command/                    # Commands (Write operations)
│   ├── command/handler/            # Command Handlers
│   ├── query/                      # Queries (Read operations)
│   ├── query/handler/              # Query Handlers
│   ├── dto/                        # Data Transfer Objects
│   └── mapper/                     # DTO Mappers
├── infrastructure/                 # Infrastructure Layer
│   └── repository/                 # Repository Implementations
└── presentation/                   # Presentation Layer
    └── controller/                 # REST Controllers
```

## Features

### Domain Model

- **Car Aggregate**: Manages car inventory and availability
- **Customer Aggregate**: Manages customer information and validation
- **Rental Aggregate**: Manages rental transactions

### CQRS Implementation

**Commands (Write Side):**
- `RentCarCommand` - Create a new rental
- `ReturnCarCommand` - Return a rented car
- `CancelRentalCommand` - Cancel a rental
- `CreateCarCommand` - Add a new car to inventory
- `CreateCustomerCommand` - Register a new customer

**Queries (Read Side):**
- `GetCarQuery` - Get car details
- `GetAvailableCarsQuery` - Get list of available cars
- `GetRentalQuery` - Get rental details
- `GetCustomerRentalsQuery` - Get all rentals for a customer
- `GetCustomerQuery` - Get customer details

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. The application will start on `http://localhost:8080`

6. Access H2 Console at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:carrentdb`
   - Username: `sa`
   - Password: (empty)

## API Endpoints

### Cars

- `POST /api/cars` - Create a new car
- `GET /api/cars/{id}` - Get car by ID
- `GET /api/cars/available` - Get all available cars

### Customers

- `POST /api/customers` - Create a new customer
- `GET /api/customers/{id}` - Get customer by ID

### Rentals

- `POST /api/rentals` - Rent a car
- `POST /api/rentals/{id}/return` - Return a rented car
- `POST /api/rentals/{id}/cancel` - Cancel a rental
- `GET /api/rentals/{id}` - Get rental by ID
- `GET /api/rentals/customer/{customerId}` - Get all rentals for a customer

## Example API Requests

### Create a Car

```bash
POST /api/cars
Content-Type: application/json

{
  "make": "Toyota",
  "model": "Camry",
  "year": 2023,
  "color": "Blue",
  "licensePlateNumber": "ABC-123",
  "licensePlateCountry": "US",
  "mileage": 10000
}
```

### Create a Customer

```bash
POST /api/customers
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "licenseNumber": "DL123456",
  "licenseExpiryDate": "2025-12-31T00:00:00"
}
```

### Rent a Car

```bash
POST /api/rentals
Content-Type: application/json

{
  "customerId": 1,
  "carId": 1,
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-05T10:00:00"
}
```

### Return a Car

```bash
POST /api/rentals/1/return
Content-Type: application/json

{
  "newMileage": 10500
}
```

## Domain Rules

1. **Rental Validation:**
   - Customer must be active
   - Customer license must be valid (not expired)
   - Car must be available
   - Rental period cannot exceed 30 days
   - No overlapping rentals for the same car

2. **Car Status:**
   - Cars can be: AVAILABLE, RENTED, MAINTENANCE, RETIRED
   - Only available cars can be rented
   - Rented cars cannot be scheduled for maintenance

3. **Rental Status:**
   - Rentals can be: PENDING, ACTIVE, COMPLETED, CANCELLED
   - Only active rentals can be completed
   - Completed rentals cannot be cancelled

4. **Cost Calculation:**
   - Default daily rate: $50.00 USD
   - Total cost = daily rate × number of days

## Testing

Run tests with:
```bash
mvn test
```

## Database Configuration

### Development (H2)
The application uses H2 in-memory database by default. Configuration is in `application.yml`.

### Production (PostgreSQL)
To use PostgreSQL, update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/carrentdb
    username: postgres
    password: postgres
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

## Future Enhancements

- [ ] Event sourcing for audit trail
- [ ] Read model projections for optimized queries
- [ ] Integration with external payment services
- [ ] Car location tracking
- [ ] Advanced search and filtering
- [ ] Reservation system
- [ ] Customer loyalty program
- [ ] Reporting and analytics

## License

This project is for educational purposes.

## Author

Car Rental Application - DDD/CQRS Implementation
