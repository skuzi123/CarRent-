INSERT INTO customer.customers (
    id,
    created_at,
    license_expiry_date,
    updated_at,
    email,
    first_name,
    last_name,
    license_number,
    phone,
    status
) VALUES
    (
        1,
        CURRENT_TIMESTAMP(),
        DATEADD('YEAR', 3, CURRENT_TIMESTAMP()),
        CURRENT_TIMESTAMP(),
        'jan.kowalski@example.com',
        'Jan',
        'Kowalski',
        'KR1234567',
        '+48100100100',
        'ACTIVE'
    ),
    (
        2,
        CURRENT_TIMESTAMP(),
        DATEADD('YEAR', 2, CURRENT_TIMESTAMP()),
        CURRENT_TIMESTAMP(),
        'anna.nowak@example.com',
        'Anna',
        'Nowak',
        'WA7654321',
        '+48200200200',
        'ACTIVE'
    );

INSERT INTO car.cars (
    id,
    production_year,
    created_at,
    mileage,
    updated_at,
    color,
    license_plate_country,
    license_plate_number,
    make,
    model,
    status
) VALUES
    (
        1,
        2022,
        CURRENT_TIMESTAMP(),
        24500,
        CURRENT_TIMESTAMP(),
        'Black',
        'PL',
        'KR1A234',
        'Toyota',
        'Corolla',
        'AVAILABLE'
    ),
    (
        2,
        2023,
        CURRENT_TIMESTAMP(),
        12100,
        CURRENT_TIMESTAMP(),
        'White',
        'PL',
        'WA9B876',
        'Skoda',
        'Octavia',
        'AVAILABLE'
    );

INSERT INTO rental.rentals (
    id,
    total_cost_amount,
    car_id,
    created_at,
    customer_id,
    end_date,
    start_date,
    updated_at,
    status,
    total_cost_currency
) VALUES
    (
        1,
        450.00,
        1,
        CURRENT_TIMESTAMP(),
        1,
        DATEADD('DAY', 3, CURRENT_TIMESTAMP()),
        CURRENT_TIMESTAMP(),
        CURRENT_TIMESTAMP(),
        'ACTIVE',
        'PLN'
    );
