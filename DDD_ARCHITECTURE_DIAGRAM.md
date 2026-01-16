# Domain-Driven Design Architecture Diagram
## Car Rental Application

## 1. Bounded Contexts

```
┌─────────────────────────────────────────────────────────────────┐
│                    CAR RENTAL APPLICATION                       │
└─────────────────────────────────────────────────────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   RENTAL      │    │   INVENTORY   │    │   CUSTOMER    │
│  MANAGEMENT   │    │   MANAGEMENT  │    │  MANAGEMENT   │
│   CONTEXT     │    │    CONTEXT    │    │    CONTEXT    │
└───────────────┘    └───────────────┘    └───────────────┘
```

## 2. Domain Model - Rental Management Context

### Aggregates

```
┌─────────────────────────────────────────────────────────────┐
│                    RENTAL AGGREGATE                          │
├─────────────────────────────────────────────────────────────┤
│ Aggregate Root: Rental                                       │
│                                                              │
│ Entities:                                                    │
│   - Rental (id, customerId, carId, startDate, endDate,      │
│            status, totalCost)                                │
│   - RentalItem (optional for additional services)           │
│                                                              │
│ Value Objects:                                               │
│   - RentalPeriod (startDate, endDate)                       │
│   - Money (amount, currency)                                 │
│   - RentalStatus (PENDING, ACTIVE, COMPLETED, CANCELLED)    │
│                                                              │
│ Domain Services:                                             │
│   - RentalCalculationService                                 │
│   - RentalValidationService                                 │
│                                                              │
│ Domain Events:                                               │
│   - RentalCreated                                            │
│   - RentalCompleted                                          │
│   - RentalCancelled                                          │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    CAR AGGREGATE                              │
├─────────────────────────────────────────────────────────────┤
│ Aggregate Root: Car                                          │
│                                                              │
│ Entities:                                                    │
│   - Car (id, make, model, year, licensePlate,               │
│         mileage, status)                                     │
│                                                              │
│ Value Objects:                                               │
│   - CarSpecification (make, model, year, color)             │
│   - LicensePlate (number, country)                          │
│   - CarStatus (AVAILABLE, RENTED, MAINTENANCE, RETIRED)     │
│   - Location (address, city, country)                       │
│                                                              │
│ Domain Services:                                             │
│   - CarAvailabilityService                                   │
│                                                              │
│ Domain Events:                                               │
│   - CarRented                                                │
│   - CarReturned                                              │
│   - CarMaintenanceScheduled                                  │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                  CUSTOMER AGGREGATE                           │
├─────────────────────────────────────────────────────────────┤
│ Aggregate Root: Customer                                     │
│                                                              │
│ Entities:                                                    │
│   - Customer (id, firstName, lastName, email, phone,        │
│              licenseNumber, status)                          │
│                                                              │
│ Value Objects:                                               │
│   - PersonalInfo (firstName, lastName, email, phone)        │
│   - LicenseInfo (licenseNumber, expiryDate, country)        │
│   - CustomerStatus (ACTIVE, SUSPENDED, INACTIVE)            │
│                                                              │
│ Domain Services:                                             │
│   - CustomerValidationService                                │
│                                                              │
│ Domain Events:                                               │
│   - CustomerRegistered                                       │
│   - CustomerSuspended                                        │
└─────────────────────────────────────────────────────────────┘
```

## 3. CQRS Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                        API LAYER                              │
│                    (REST Controllers)                         │
└──────────────────────────────────────────────────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   COMMANDS    │    │    QUERIES    │    │   EVENTS      │
│               │    │               │    │               │
│ - RentCar     │    │ - GetCar      │    │ - EventBus    │
│ - ReturnCar   │    │ - GetRentals  │    │               │
│ - ReserveCar  │    │ - GetCustomer │    │               │
│ - CancelRental│    │ - GetAvailable│    │               │
└───────────────┘    └───────────────┘    └───────────────┘
        │                     │                     │
        ▼                     ▼                     ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│  COMMAND      │    │   QUERY       │    │   EVENT       │
│  HANDLERS     │    │   HANDLERS    │    │   HANDLERS    │
│               │    │               │    │               │
│ - RentCar     │    │ - CarQuery    │    │ - Rental      │
│   Handler     │    │   Handler     │    │   Event       │
│ - ReturnCar   │    │ - RentalQuery │    │   Handler     │
│   Handler     │    │   Handler     │    │               │
└───────────────┘    └───────────────┘    └───────────────┘
        │                     │                     │
        ▼                     ▼                     ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   DOMAIN      │    │   READ MODEL  │    │   EVENT       │
│   LAYER       │    │   (Projection)│    │   STORE       │
│               │    │               │    │               │
│ - Aggregates  │    │ - CarView     │    │ - Event       │
│ - Entities    │    │ - RentalView  │    │   Repository  │
│ - Services    │    │ - CustomerView│    │               │
└───────────────┘    └───────────────┘    └───────────────┘
        │                     │                     │
        └─────────────────────┼─────────────────────┘
                              ▼
                    ┌─────────────────┐
                    │   INFRASTRUCTURE│
                    │      LAYER      │
                    │                 │
                    │ - Repositories  │
                    │ - Database      │
                    │ - External APIs │
                    └─────────────────┘
```

## 4. Layer Architecture (Onion Architecture)

```
                    ┌─────────────────────┐
                    │   PRESENTATION      │
                    │   (Controllers)     │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │   APPLICATION       │
                    │   (Use Cases)       │
                    │   - Commands        │
                    │   - Queries         │
                    │   - DTOs            │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │   DOMAIN            │
                    │   (Core Business)   │
                    │   - Aggregates      │
                    │   - Entities        │
                    │   - Value Objects   │
                    │   - Domain Services │
                    │   - Domain Events   │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │   INFRASTRUCTURE     │
                    │   (Technical)       │
                    │   - Repositories    │
                    │   - Database        │
                    │   - Messaging       │
                    └─────────────────────┘
```

## 5. Command Flow Example: RentCar

```
1. Client Request
   │
   ▼
2. REST Controller (RentalController.rentCar)
   │
   ▼
3. Command (RentCarCommand)
   │
   ▼
4. Command Handler (RentCarCommandHandler)
   │
   ├─► Load Aggregate (RentalRepository.findById)
   │
   ├─► Domain Service (RentalValidationService.validate)
   │
   ├─► Execute Business Logic (Rental.rent())
   │
   ├─► Save Aggregate (RentalRepository.save)
   │
   └─► Publish Domain Event (RentalCreated)
       │
       ▼
5. Event Handler (UpdateCarStatusHandler)
   │
   ▼
6. Update Read Model (CarViewRepository.update)
```

## 6. Query Flow Example: GetAvailableCars

```
1. Client Request
   │
   ▼
2. REST Controller (CarController.getAvailableCars)
   │
   ▼
3. Query (GetAvailableCarsQuery)
   │
   ▼
4. Query Handler (GetAvailableCarsQueryHandler)
   │
   ▼
5. Read Model Repository (CarViewRepository.findAvailable)
   │
   ▼
6. Return DTO (CarViewDTO)
```

## 7. Key Domain Concepts

### Entities
- **Rental**: Represents a rental transaction
- **Car**: Represents a vehicle in the fleet
- **Customer**: Represents a customer who can rent cars

### Value Objects
- **RentalPeriod**: Immutable period with start and end dates
- **Money**: Represents monetary value with currency
- **CarSpecification**: Car details (make, model, year, color)
- **LicensePlate**: Vehicle registration information
- **Location**: Physical location information

### Domain Services
- **RentalCalculationService**: Calculates rental costs
- **RentalValidationService**: Validates rental business rules
- **CarAvailabilityService**: Checks car availability
- **CustomerValidationService**: Validates customer eligibility

### Domain Events
- **RentalCreated**: Published when a rental is created
- **RentalCompleted**: Published when a rental is completed
- **RentalCancelled**: Published when a rental is cancelled
- **CarRented**: Published when a car is rented
- **CarReturned**: Published when a car is returned

## 8. Repository Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                    REPOSITORY INTERFACES                      │
│                    (Domain Layer)                            │
├─────────────────────────────────────────────────────────────┤
│ - RentalRepository (save, findById, findByCustomerId)       │
│ - CarRepository (save, findById, findByStatus)              │
│ - CustomerRepository (save, findById, findByEmail)          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                 REPOSITORY IMPLEMENTATIONS                    │
│                 (Infrastructure Layer)                       │
├─────────────────────────────────────────────────────────────┤
│ - JpaRentalRepository (implements RentalRepository)         │
│ - JpaCarRepository (implements CarRepository)               │
│ - JpaCustomerRepository (implements CustomerRepository)     │
└─────────────────────────────────────────────────────────────┘
```

## 9. CQRS Separation

### Write Side (Commands)
- **Command**: Immutable data object representing intent
- **Command Handler**: Orchestrates domain logic
- **Write Repository**: Persists aggregates
- **Event Publisher**: Publishes domain events

### Read Side (Queries)
- **Query**: Immutable data object representing read request
- **Query Handler**: Retrieves data from read model
- **Read Repository**: Accesses optimized read views
- **Projection**: Updates read model from events

## 10. Technology Stack

```
┌─────────────────────────────────────────────────────────────┐
│                    TECHNOLOGY STACK                          │
├─────────────────────────────────────────────────────────────┤
│ Framework:     Spring Boot 3.x                               │
│ Language:      Java 17+                                      │
│ Database:      PostgreSQL / H2 (for development)            │
│ ORM:           Spring Data JPA                               │
│ CQRS:          Axon Framework (optional) or custom           │
│ Event Bus:     Spring Events / RabbitMQ (optional)           │
│ API:           REST (Spring Web)                             │
│ Validation:    Bean Validation (JSR-303)                    │
│ Testing:       JUnit 5, Mockito, Testcontainers             │
└─────────────────────────────────────────────────────────────┘
```
