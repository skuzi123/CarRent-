package com.carrent.application.query.handler;

import com.carrent.application.dto.CustomerDTO;
import com.carrent.application.mapper.CustomerMapper;
import com.carrent.application.query.GetCustomerQuery;
import com.carrent.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerQueryHandler {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public GetCustomerQueryHandler(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO handle(GetCustomerQuery query) {
        return customerRepository.findById(query.getCustomerId())
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + query.getCustomerId()));
    }
}
