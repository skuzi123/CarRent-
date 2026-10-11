package com.carrent.boundedcontext.rental.application.query;

import lombok.Value;

@Value
public class GetCustomerRentalsQuery {
    Long customerId;
}
